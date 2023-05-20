package com.eps.todoturtle.aboutus.logic.client

import android.content.Context
import com.eps.todoturtle.R
import com.eps.todoturtle.aboutus.logic.routes.AboutUsRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class APIClient(val context: Context) {
    private val client: HttpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getText(): String {
        return try {
            client.get(context.getString(AboutUsRoutes.TEXT.stringResId)).bodyAsText()
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }

    suspend fun getImageUrl(): String {
        return client.get(context.getString(AboutUsRoutes.IMAGE.stringResId)).bodyAsText()
    }
}
