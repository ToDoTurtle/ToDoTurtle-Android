package com.eps.todoturtle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import com.eps.todoturtle.preferences.logic.data.AppPreferences
import com.eps.todoturtle.preferences.logic.data.Themes
import com.eps.todoturtle.ui.theme.amber.AmberTheme
import com.eps.todoturtle.ui.theme.cold.ColdTheme
import com.eps.todoturtle.ui.theme.colddark.ColdDarkTheme
import com.eps.todoturtle.ui.theme.def.DefaultTheme
import com.eps.todoturtle.ui.theme.greenish.GreenishTheme
import com.eps.todoturtle.ui.theme.magenta.MagentaTheme

val ColorScheme.noteContainer: Color
    @Composable
    get() = secondaryContainer

val ColorScheme.onNoteContainer: Color
    @Composable
    get() = onSecondaryContainer

val ColorScheme.activeOnSecondaryContainer: Color
    @Composable
    get() = onSecondaryContainer

val ColorScheme.inactiveOnSecondaryContainer: Color
    @Composable
    get() = if (isSystemInDarkTheme()) onSecondary else outline

val ColorScheme.noteHeadlineContainer: Color
    @Composable
    get() = tertiaryContainer

val ColorScheme.onNoteHeadlineContainer: Color
    @Composable
    get() = onSurface

val ColorScheme.onNoteScreenButton: Color
    @Composable
    get() = onPrimaryContainer

val ColorScheme.noteScreenButton: Color
    @Composable
    get() = primaryContainer

val ColorScheme.formContainer: Color
    @Composable
    get() = noteScreenButton

val ColorScheme.onFormContainer: Color
    @Composable
    get() = onNoteScreenButton

@Composable
fun ToDoTurtleTheme(
    storage: DataStore<AppPreferences>,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val preferences: AppPreferences? = storage.data.collectAsState(null).value
    when (preferences?.theme) {
        Themes.Greenish -> GreenishTheme(useDarkTheme, content)
        Themes.Cold -> ColdTheme(useDarkTheme, content)
        Themes.ColdDark -> ColdDarkTheme(useDarkTheme, content)
        Themes.Amber -> AmberTheme(useDarkTheme, content)
        Themes.Magenta -> MagentaTheme(useDarkTheme, content)
        Themes.Default -> DefaultTheme(useDarkTheme, content)
        else -> {}
    }
}
