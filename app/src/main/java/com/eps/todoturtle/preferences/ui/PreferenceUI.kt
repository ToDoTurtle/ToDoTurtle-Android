package com.eps.todoturtle.preferences.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eps.todoturtle.R

@Composable
fun PreferenceUI() {
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
                    checked = false,  // FIXME: Implement data store logic
                    onCheckedChange = { }
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
    PreferenceUI()
}