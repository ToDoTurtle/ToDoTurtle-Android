package com.eps.todoturtle.preferences.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import com.eps.todoturtle.R
import com.eps.todoturtle.preferences.logic.AppPreferencesModifier
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.shared.logic.extensions.dataStore
import kotlinx.coroutines.launch

@Composable
fun PreferenceUI(
    dataStore: DataStore<AppPreferences>,
) {
    val currentPreferences: AppPreferences = dataStore.data.collectAsState(initial = AppPreferences()).value
    val preferenceMod = AppPreferencesModifier(dataStore)
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
        ) {
            PreferenceGroup(
                R.string.category_sound
            ) {
                PreferenceSwitch(
                    icon = R.drawable.mute,
                    iconDesc = R.string.mute_image_desc,
                    text = R.string.mute_desc,
                    checked = currentPreferences.mute,  // FIXME: Implement data store logic
                    onCheckedChange = {
                        coroutineScope.launch {
                            preferenceMod.updateMute()
                        }
                    }
                )
            }
            PreferenceGroup(
                groupTitle = R.string.category_theme,
            ) {
                
            }
        }
    }
}


@Preview
@Composable
fun PreferenceUIPreview() {
    PreferenceUI(LocalContext.current.dataStore)
}