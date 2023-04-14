package com.eps.todoturtle.preferences.logic

import android.util.Log
import androidx.datastore.core.Serializer
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppPreferencesSerializer : Serializer<AppPreferences> {
    override val defaultValue: AppPreferences
        get() = AppPreferences()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        return try {
            Json.decodeFromString(
                deserializer = AppPreferences.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            Log.e("AppPreferencesSerializer", "Error reading preferences")
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
        @Suppress("BlockingMethodInNonBlockingContext")
        // It's already running asynchronously, but Studio's not realizing it
        output.write(
            Json.encodeToString(
                serializer = AppPreferences.serializer(),
                value = t,
            ).encodeToByteArray(),
        )
    }
}