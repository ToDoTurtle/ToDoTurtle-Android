package com.eps.todoturtle.preferences.logic

import androidx.datastore.core.DataStore
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.logic.data.Themes

class AppPreferencesModifier(
    private val dataStore: DataStore<AppPreferences>,
) {
    suspend fun updateMute(mute: Boolean) {
        dataStore.updateData { preferences ->
            preferences.copy(mute = mute)
        }
    }

    suspend fun updateTheme(theme: Themes) {
        dataStore.updateData { preferences ->
            preferences.copy(theme = theme)
        }
    }
}