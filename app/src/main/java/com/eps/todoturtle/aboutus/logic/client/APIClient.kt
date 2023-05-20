package com.eps.todoturtle.aboutus.logic.client

import com.eps.todoturtle.aboutus.logic.routes.AboutUsRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class APIClient {
    private val client: HttpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getText(): String {
        return try {
            client.get(AboutUsRoutes.TEXT.toString()).bodyAsText()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    suspend fun getImageUrl(): String {
        return client.get(AboutUsRoutes.IMAGE.toString()).bodyAsText()
    }
}
