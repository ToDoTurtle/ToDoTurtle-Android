package com.eps.todoturtle.note.infra

import android.util.Log
import com.eps.todoturtle.note.logic.DeadlineRepository
import com.eps.todoturtle.note.logic.Note
import com.eps.todoturtle.shared.infra.AWS_API_URL
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
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class AwsDeadlineRepository : DeadlineRepository {

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

    override suspend fun saveDeadline(note: Note): Boolean {
        return try {
            note.deadlineTime?.let { time ->
                client.post(getUrl(note)) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        mapOf(
                            "time" to time.time,
                        )
                    )
                }
            }
            true
        } catch (e: Throwable) {
            Log.e("AwsDeadlineRepository", "Error Saving: ${e.message}")
            false
        }
    }

    override suspend fun removeDeadline(note: Note) {
        try {
            client.delete(getUrl(note))
        } catch (e: Throwable) {
            Log.e("AwsDeadlineRepository", "Error Removing: ${e.message}")
        }
    }

    private fun getUrl(note: Note): String {
        val userId = Firebase.auth.currentUser!!.uid
        return AWS_API_URL + "/user/${userId}/note/${note.identifier}/deadline"
    }
}