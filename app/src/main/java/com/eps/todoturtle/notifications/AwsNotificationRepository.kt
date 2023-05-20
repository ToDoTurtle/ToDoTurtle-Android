package com.eps.todoturtle.notifications

import android.util.Log
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.shared.infra.AWS_API_URL
import com.eps.todoturtle.shared.infra.getDevicesTokens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class Notification(
    val title: String,
    val description: String,
    val devices: List<String>,
    val timeout: Long,
)

class AwsNotificationRepository : NoteNotificationRepository {

    private val client: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    override suspend fun saveNotification(note: Note): Boolean {
        return note.notificationTime.let { time ->
            val notification = Notification(
                title = note.title,
                description = note.description,
                devices = getDevicesTokens() as List<String>,
                timeout = note.notificationTime!!.time,
            )
            val result = client.post(getUrl(note)) {
                contentType(ContentType.Application.Json)
                setBody(notification)
            }
            if (result.status.isSuccess()) {
                true
            } else {
                Log.e("AwsNotificationRepository", "Notification not sent: ${result.bodyAsText()}")
                false
            }
        }
    }

    override suspend fun removeNotification(note: Note) {
        val result = client.delete(getUrl(note))
        if (!result.status.isSuccess()) {
            Log.e("AwsNotificationRepository", "Error Removing: ${result.bodyAsText()}")
        }
    }

    private fun getUrl(note: Note): String {
        val userId = Firebase.auth.currentUser!!.uid
        return AWS_API_URL + "/user/${userId}/note/${note.identifier}/notify"
    }

}