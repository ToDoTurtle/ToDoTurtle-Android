package com.eps.todoturtle.ui.theme.greenish

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val GreenishLightColors = lightColorScheme(
    primary = GreenishLightColor.primary,
    onPrimary = GreenishLightColor.onPrimary,
    primaryContainer = GreenishLightColor.primaryContainer,
    onPrimaryContainer = GreenishLightColor.onPrimaryContainer,
    secondary = GreenishLightColor.secondary,
    onSecondary = GreenishLightColor.onSecondary,
    secondaryContainer = GreenishLightColor.secondaryContainer,
    onSecondaryContainer = GreenishLightColor.onSecondaryContainer,
    tertiary = GreenishLightColor.tertiary,
    onTertiary = GreenishLightColor.onTertiary,
    tertiaryContainer = GreenishLightColor.tertiaryContainer,
    onTertiaryContainer = GreenishLightColor.onTertiaryContainer,
    error = GreenishLightColor.error,
    errorContainer = GreenishLightColor.errorContainer,
    onError = GreenishLightColor.onError,
    onErrorContainer = GreenishLightColor.onErrorContainer,
    background = GreenishLightColor.background,
    onBackground = GreenishLightColor.onBackground,
    surface = GreenishLightColor.surface,
    onSurface = GreenishLightColor.onSurface,
    surfaceVariant = GreenishLightColor.surfaceVariant,
    onSurfaceVariant = GreenishLightColor.onSurfaceVariant,
    outline = GreenishLightColor.outline,
    inverseOnSurface = GreenishLightColor.inverseOnSurface,
    inverseSurface = GreenishLightColor.inverseSurface,
    inversePrimary = GreenishLightColor.inversePrimary,
    surfaceTint = GreenishLightColor.surfaceTint,
)

private val GreenishDarkColors = darkColorScheme(
    primary = GreenishDarkColor.primary,
    onPrimary = GreenishDarkColor.onPrimary,
    primaryContainer = GreenishDarkColor.primaryContainer,
    onPrimaryContainer = GreenishDarkColor.onPrimaryContainer,
    secondary = GreenishDarkColor.secondary,
    onSecondary = GreenishDarkColor.onSecondary,
    secondaryContainer = GreenishDarkColor.secondaryContainer,
    onSecondaryContainer = GreenishDarkColor.onSecondaryContainer,
    tertiary = GreenishDarkColor.tertiary,
    onTertiary = GreenishDarkColor.onTertiary,
    tertiaryContainer = GreenishDarkColor.tertiaryContainer,
    onTertiaryContainer = GreenishDarkColor.onTertiaryContainer,
    error = GreenishDarkColor.error,
    errorContainer = GreenishDarkColor.errorContainer,
    onError = GreenishDarkColor.onError,
    onErrorContainer = GreenishDarkColor.onErrorContainer,
    background = GreenishDarkColor.background,
    onBackground = GreenishDarkColor.onBackground,
    surface = GreenishDarkColor.surface,
    onSurface = GreenishDarkColor.onSurface,
    surfaceVariant = GreenishDarkColor.surfaceVariant,
    onSurfaceVariant = GreenishDarkColor.onSurfaceVariant,
    outline = GreenishDarkColor.outline,
    inverseOnSurface = GreenishDarkColor.inverseOnSurface,
    inverseSurface = GreenishDarkColor.inverseSurface,
    inversePrimary = GreenishDarkColor.inversePrimary,
    surfaceTint = GreenishDarkColor.surfaceTint,
)

@Composable
fun GreenishTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if(!useDarkTheme)
        GreenishLightColors
    else
        GreenishDarkColors

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
