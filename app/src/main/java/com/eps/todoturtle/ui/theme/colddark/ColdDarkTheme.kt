package com.eps.todoturtle.ui.theme.colddark

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColdDarkLightColors = lightColorScheme(
    primary = ColdDarkLightColor.primary,
    onPrimary = ColdDarkLightColor.onPrimary,
    primaryContainer = ColdDarkLightColor.primaryContainer,
    onPrimaryContainer = ColdDarkLightColor.onPrimaryContainer,
    secondary = ColdDarkLightColor.secondary,
    onSecondary = ColdDarkLightColor.onSecondary,
    secondaryContainer = ColdDarkLightColor.secondaryContainer,
    onSecondaryContainer = ColdDarkLightColor.onSecondaryContainer,
    tertiary = ColdDarkLightColor.tertiary,
    onTertiary = ColdDarkLightColor.onTertiary,
    tertiaryContainer = ColdDarkLightColor.tertiaryContainer,
    onTertiaryContainer = ColdDarkLightColor.onTertiaryContainer,
    error = ColdDarkLightColor.error,
    errorContainer = ColdDarkLightColor.errorContainer,
    onError = ColdDarkLightColor.onError,
    onErrorContainer = ColdDarkLightColor.onErrorContainer,
    background = ColdDarkLightColor.background,
    onBackground = ColdDarkLightColor.onBackground,
    surface = ColdDarkLightColor.surface,
    onSurface = ColdDarkLightColor.onSurface,
    surfaceVariant = ColdDarkLightColor.surfaceVariant,
    onSurfaceVariant = ColdDarkLightColor.onSurfaceVariant,
    outline = ColdDarkLightColor.outline,
    inverseOnSurface = ColdDarkLightColor.inverseOnSurface,
    inverseSurface = ColdDarkLightColor.inverseSurface,
    inversePrimary = ColdDarkLightColor.inversePrimary,
    surfaceTint = ColdDarkLightColor.surfaceTint,
)

private val ColdDarkDarkColors = darkColorScheme(
    primary = ColdDarkDarkColor.primary,
    onPrimary = ColdDarkDarkColor.onPrimary,
    primaryContainer = ColdDarkDarkColor.primaryContainer,
    onPrimaryContainer = ColdDarkDarkColor.onPrimaryContainer,
    secondary = ColdDarkDarkColor.secondary,
    onSecondary = ColdDarkDarkColor.onSecondary,
    secondaryContainer = ColdDarkDarkColor.secondaryContainer,
    onSecondaryContainer = ColdDarkDarkColor.onSecondaryContainer,
    tertiary = ColdDarkDarkColor.tertiary,
    onTertiary = ColdDarkDarkColor.onTertiary,
    tertiaryContainer = ColdDarkDarkColor.tertiaryContainer,
    onTertiaryContainer = ColdDarkDarkColor.onTertiaryContainer,
    error = ColdDarkDarkColor.error,
    errorContainer = ColdDarkDarkColor.errorContainer,
    onError = ColdDarkDarkColor.onError,
    onErrorContainer = ColdDarkDarkColor.onErrorContainer,
    background = ColdDarkDarkColor.background,
    onBackground = ColdDarkDarkColor.onBackground,
    surface = ColdDarkDarkColor.surface,
    onSurface = ColdDarkDarkColor.onSurface,
    surfaceVariant = ColdDarkDarkColor.surfaceVariant,
    onSurfaceVariant = ColdDarkDarkColor.onSurfaceVariant,
    outline = ColdDarkDarkColor.outline,
    inverseOnSurface = ColdDarkDarkColor.inverseOnSurface,
    inverseSurface = ColdDarkDarkColor.inverseSurface,
    inversePrimary = ColdDarkDarkColor.inversePrimary,
    surfaceTint = ColdDarkDarkColor.surfaceTint,
)

@Composable
fun ColdDarkTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme)
        ColdDarkLightColors
    else
        ColdDarkDarkColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}