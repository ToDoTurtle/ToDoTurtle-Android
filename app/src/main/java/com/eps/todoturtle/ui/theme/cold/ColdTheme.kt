package com.eps.todoturtle.ui.theme.cold

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColdLightColors = lightColorScheme(
    primary = ColdLightColor.primary,
    onPrimary = ColdLightColor.onPrimary,
    primaryContainer = ColdLightColor.primaryContainer,
    onPrimaryContainer = ColdLightColor.onPrimaryContainer,
    secondary = ColdLightColor.secondary,
    onSecondary = ColdLightColor.onSecondary,
    secondaryContainer = ColdLightColor.secondaryContainer,
    onSecondaryContainer = ColdLightColor.onSecondaryContainer,
    tertiary = ColdLightColor.tertiary,
    onTertiary = ColdLightColor.onTertiary,
    tertiaryContainer = ColdLightColor.tertiaryContainer,
    onTertiaryContainer = ColdLightColor.onTertiaryContainer,
    error = ColdLightColor.error,
    errorContainer = ColdLightColor.errorContainer,
    onError = ColdLightColor.onError,
    onErrorContainer = ColdLightColor.onErrorContainer,
    background = ColdLightColor.background,
    onBackground = ColdLightColor.onBackground,
    surface = ColdLightColor.surface,
    onSurface = ColdLightColor.onSurface,
    surfaceVariant = ColdLightColor.surfaceVariant,
    onSurfaceVariant = ColdLightColor.onSurfaceVariant,
    outline = ColdLightColor.outline,
    inverseOnSurface = ColdLightColor.inverseOnSurface,
    inverseSurface = ColdLightColor.inverseSurface,
    inversePrimary = ColdLightColor.inversePrimary,
    surfaceTint = ColdLightColor.surfaceTint,
)

val ColdDarkColors = darkColorScheme(
    primary = ColdDarkColor.primary,
    onPrimary = ColdDarkColor.onPrimary,
    primaryContainer = ColdDarkColor.primaryContainer,
    onPrimaryContainer = ColdDarkColor.onPrimaryContainer,
    secondary = ColdDarkColor.secondary,
    onSecondary = ColdDarkColor.onSecondary,
    secondaryContainer = ColdDarkColor.secondaryContainer,
    onSecondaryContainer = ColdDarkColor.onSecondaryContainer,
    tertiary = ColdDarkColor.tertiary,
    onTertiary = ColdDarkColor.onTertiary,
    tertiaryContainer = ColdDarkColor.tertiaryContainer,
    onTertiaryContainer = ColdDarkColor.onTertiaryContainer,
    error = ColdDarkColor.error,
    errorContainer = ColdDarkColor.errorContainer,
    onError = ColdDarkColor.onError,
    onErrorContainer = ColdDarkColor.onErrorContainer,
    background = ColdDarkColor.background,
    onBackground = ColdDarkColor.onBackground,
    surface = ColdDarkColor.surface,
    onSurface = ColdDarkColor.onSurface,
    surfaceVariant = ColdDarkColor.surfaceVariant,
    onSurfaceVariant = ColdDarkColor.onSurfaceVariant,
    outline = ColdDarkColor.outline,
    inverseOnSurface = ColdDarkColor.inverseOnSurface,
    inverseSurface = ColdDarkColor.inverseSurface,
    inversePrimary = ColdDarkColor.inversePrimary,
    surfaceTint = ColdDarkColor.surfaceTint,
)

@Composable
fun ColdTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) {
        ColdDarkColors
    } else {
        ColdLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
