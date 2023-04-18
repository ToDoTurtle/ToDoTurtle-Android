package com.eps.todoturtle.ui.theme.def

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.eps.todoturtle.ui.theme.cold.ColdDarkColors
import com.eps.todoturtle.ui.theme.greenish.GreenishLightColors

@Composable
fun DefaultTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (!useDarkTheme) {
        GreenishLightColors
    } else {
        ColdDarkColors
    }
    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
