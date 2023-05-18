package com.eps.todoturtle.preferences.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import com.eps.todoturtle.R
import com.eps.todoturtle.network.logic.NetworkAvailability
import com.eps.todoturtle.preferences.logic.AppPreferencesModifier
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.shared.logic.extensions.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun PreferenceUI(
    dataStore: DataStore<AppPreferences>,
) {
    val currentPreferences: AppPreferences =
        dataStore.data.collectAsState(initial = AppPreferences()).value
    val preferenceMod = AppPreferencesModifier(dataStore, LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
        ) {
            PreferenceGroup(
                R.string.preference_category_general,
            ) {
                PreferenceSwitch(
                    icon = R.drawable.headphones,
                    iconDesc = R.string.preference_mute_image_desc,
                    text = R.string.preference_mute_desc,
                    checked = currentPreferences.mute,
                    onCheckedChange = {
                        coroutineScope.launch {
                            preferenceMod.updateMute(it)
                        }
                    },
                )
                PreferenceDropdown(
                    icon = R.drawable.palette,
                    iconDesc = R.string.preference_theme_image_desc,
                    text = R.string.preference_theme_desc,
                    selected = currentPreferences.theme,
                ) {
                    coroutineScope.launch {
                        preferenceMod.updateTheme(it)
                    }
                }
            }
            PreferenceGroup(
                R.string.preference_category_network,
            ) {
                PreferenceSwitch(
                    icon = R.drawable.only_wifi,
                    iconDesc = R.string.preference_wifi_image_desc,
                    text = R.string.preferences_only_wifi_desc,
                    checked = currentPreferences.onlyWifi,
                    onCheckedChange = {
                        coroutineScope.launch {
                            preferenceMod.updateOnlyWifi(it)
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun PreferenceUIWithoutConnection(
    dataStore: DataStore<AppPreferences>,
    networkAvailability: Flow<NetworkAvailability>,
) {
    val currentPreferences: AppPreferences =
        dataStore.data.collectAsState(initial = AppPreferences()).value
    val preferenceMod = AppPreferencesModifier(dataStore, LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
        ) {
            PreferenceGroup(
                R.string.preference_category_general,
            ) {
                PreferenceSwitch(
                    icon = R.drawable.headphones,
                    iconDesc = R.string.preference_mute_image_desc,
                    text = R.string.preference_mute_desc,
                    checked = currentPreferences.mute,
                    onCheckedChange = {
                        coroutineScope.launch {
                            preferenceMod.updateMute(it)
                        }
                    },
                )
                PreferenceDropdown(
                    icon = R.drawable.palette,
                    iconDesc = R.string.preference_theme_image_desc,
                    text = R.string.preference_theme_desc,
                    selected = currentPreferences.theme,
                ) {
                    coroutineScope.launch {
                        preferenceMod.updateTheme(it)
                    }
                }
            }
            PreferenceGroup(
                R.string.preference_category_network,
            ) {
                PreferenceSwitch(
                    icon = R.drawable.only_wifi,
                    iconDesc = R.string.preference_wifi_image_desc,
                    text = R.string.preferences_only_wifi_desc,
                    checked = currentPreferences.onlyWifi,
                    onCheckedChange = {
                        coroutineScope.launch {
                            preferenceMod.updateOnlyWifi(it)
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreferenceUIPreview() {
    PreferenceUI(LocalContext.current.dataStore)
}
