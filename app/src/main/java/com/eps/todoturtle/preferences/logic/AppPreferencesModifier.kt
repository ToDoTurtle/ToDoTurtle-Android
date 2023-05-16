package com.eps.todoturtle.preferences.logic

import android.content.Context
import androidx.datastore.core.DataStore
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.logic.data.PreferenceEnum
import com.eps.todoturtle.preferences.logic.data.Themes

class AppPreferencesModifier(
    private val dataStore: DataStore<AppPreferences>,
    private val context: Context,
) {
    private val muteModifier = MuteModifier(context)

    suspend fun updateMute(mute: Boolean) {
        dataStore.updateData { preferences ->
            preferences.copy(mute = mute)
        }
        muteModifier.updateMute(mute)
    }

    suspend fun updateOnlyWifi(onlyWifi: Boolean) {
        dataStore.updateData { preferences ->
            preferences.copy(onlyWifi = onlyWifi)
        }
    }

    suspend fun updateTheme(theme: PreferenceEnum<Themes>) {
        dataStore.updateData { preferences ->
            preferences.copy(theme = theme.getValue())
        }
    }
}
