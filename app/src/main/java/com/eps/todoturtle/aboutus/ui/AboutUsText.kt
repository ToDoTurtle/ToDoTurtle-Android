package com.eps.todoturtle.aboutus.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutUsText(text: String) {
    val scroll = rememberScrollState()
    Text(
        text  = text,
        modifier = Modifier
            .height(300.dp)
            .verticalScroll(scroll)
            .padding(horizontal = 20.dp),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}