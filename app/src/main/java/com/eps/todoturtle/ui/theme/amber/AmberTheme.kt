package com.eps.todoturtle.ui.theme.amber

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AmberLightColors = lightColorScheme(
    primary = AmberLightColor.primary,
    onPrimary = AmberLightColor.onPrimary,
    primaryContainer = AmberLightColor.primaryContainer,
    onPrimaryContainer = AmberLightColor.onPrimaryContainer,
    secondary = AmberLightColor.secondary,
    onSecondary = AmberLightColor.onSecondary,
    secondaryContainer = AmberLightColor.secondaryContainer,
    onSecondaryContainer = AmberLightColor.onSecondaryContainer,
    tertiary = AmberLightColor.tertiary,
    onTertiary = AmberLightColor.onTertiary,
    tertiaryContainer = AmberLightColor.tertiaryContainer,
    onTertiaryContainer = AmberLightColor.onTertiaryContainer,
    error = AmberLightColor.error,
    errorContainer = AmberLightColor.errorContainer,
    onError = AmberLightColor.onError,
    onErrorContainer = AmberLightColor.onErrorContainer,
    background = AmberLightColor.background,
    onBackground = AmberLightColor.onBackground,
    surface = AmberLightColor.surface,
    onSurface = AmberLightColor.onSurface,
    surfaceVariant = AmberLightColor.surfaceVariant,
    onSurfaceVariant = AmberLightColor.onSurfaceVariant,
    outline = AmberLightColor.outline,
    inverseOnSurface = AmberLightColor.inverseOnSurface,
    inverseSurface = AmberLightColor.inverseSurface,
    inversePrimary = AmberLightColor.inversePrimary,
    surfaceTint = AmberLightColor.surfaceTint,
)

private val AmberDarkColors = darkColorScheme(
    primary = AmberDarkColor.primary,
    onPrimary = AmberDarkColor.onPrimary,
    primaryContainer = AmberDarkColor.primaryContainer,
    onPrimaryContainer = AmberDarkColor.onPrimaryContainer,
    secondary = AmberDarkColor.secondary,
    onSecondary = AmberDarkColor.onSecondary,
    secondaryContainer = AmberDarkColor.secondaryContainer,
    onSecondaryContainer = AmberDarkColor.onSecondaryContainer,
    tertiary = AmberDarkColor.tertiary,
    onTertiary = AmberDarkColor.onTertiary,
    tertiaryContainer = AmberDarkColor.tertiaryContainer,
    onTertiaryContainer = AmberDarkColor.onTertiaryContainer,
    error = AmberDarkColor.error,
    errorContainer = AmberDarkColor.errorContainer,
    onError = AmberDarkColor.onError,
    onErrorContainer = AmberDarkColor.onErrorContainer,
    background = AmberDarkColor.background,
    onBackground = AmberDarkColor.onBackground,
    surface = AmberDarkColor.surface,
    onSurface = AmberDarkColor.onSurface,
    surfaceVariant = AmberDarkColor.surfaceVariant,
    onSurfaceVariant = AmberDarkColor.onSurfaceVariant,
    outline = AmberDarkColor.outline,
    inverseOnSurface = AmberDarkColor.inverseOnSurface,
    inverseSurface = AmberDarkColor.inverseSurface,
    inversePrimary = AmberDarkColor.inversePrimary,
    surfaceTint = AmberDarkColor.surfaceTint,
)

@Composable
fun AmberTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) {
        AmberDarkColors
    } else {
        AmberLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
