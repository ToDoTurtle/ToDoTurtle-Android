package com.eps.todoturtle.ui.theme.magenta

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MagentaLightColors = lightColorScheme(
    primary = MagentaLightColor.primary,
    onPrimary = MagentaLightColor.onPrimary,
    primaryContainer = MagentaLightColor.primaryContainer,
    onPrimaryContainer = MagentaLightColor.onPrimaryContainer,
    secondary = MagentaLightColor.secondary,
    onSecondary = MagentaLightColor.onSecondary,
    secondaryContainer = MagentaLightColor.secondaryContainer,
    onSecondaryContainer = MagentaLightColor.onSecondaryContainer,
    tertiary = MagentaLightColor.tertiary,
    onTertiary = MagentaLightColor.onTertiary,
    tertiaryContainer = MagentaLightColor.tertiaryContainer,
    onTertiaryContainer = MagentaLightColor.onTertiaryContainer,
    error = MagentaLightColor.error,
    errorContainer = MagentaLightColor.errorContainer,
    onError = MagentaLightColor.onError,
    onErrorContainer = MagentaLightColor.onErrorContainer,
    background = MagentaLightColor.background,
    onBackground = MagentaLightColor.onBackground,
    surface = MagentaLightColor.surface,
    onSurface = MagentaLightColor.onSurface,
    surfaceVariant = MagentaLightColor.surfaceVariant,
    onSurfaceVariant = MagentaLightColor.onSurfaceVariant,
    outline = MagentaLightColor.outline,
    inverseOnSurface = MagentaLightColor.inverseOnSurface,
    inverseSurface = MagentaLightColor.inverseSurface,
    inversePrimary = MagentaLightColor.inversePrimary,
    surfaceTint = MagentaLightColor.surfaceTint,
)

private val MagentaDarkColors = darkColorScheme(
    primary = MagentaDarkColor.primary,
    onPrimary = MagentaDarkColor.onPrimary,
    primaryContainer = MagentaDarkColor.primaryContainer,
    onPrimaryContainer = MagentaDarkColor.onPrimaryContainer,
    secondary = MagentaDarkColor.secondary,
    onSecondary = MagentaDarkColor.onSecondary,
    secondaryContainer = MagentaDarkColor.secondaryContainer,
    onSecondaryContainer = MagentaDarkColor.onSecondaryContainer,
    tertiary = MagentaDarkColor.tertiary,
    onTertiary = MagentaDarkColor.onTertiary,
    tertiaryContainer = MagentaDarkColor.tertiaryContainer,
    onTertiaryContainer = MagentaDarkColor.onTertiaryContainer,
    error = MagentaDarkColor.error,
    errorContainer = MagentaDarkColor.errorContainer,
    onError = MagentaDarkColor.onError,
    onErrorContainer = MagentaDarkColor.onErrorContainer,
    background = MagentaDarkColor.background,
    onBackground = MagentaDarkColor.onBackground,
    surface = MagentaDarkColor.surface,
    onSurface = MagentaDarkColor.onSurface,
    surfaceVariant = MagentaDarkColor.surfaceVariant,
    onSurfaceVariant = MagentaDarkColor.onSurfaceVariant,
    outline = MagentaDarkColor.outline,
    inverseOnSurface = MagentaDarkColor.inverseOnSurface,
    inverseSurface = MagentaDarkColor.inverseSurface,
    inversePrimary = MagentaDarkColor.inversePrimary,
    surfaceTint = MagentaDarkColor.surfaceTint,
)

@Composable
fun MagentaTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) {
        MagentaDarkColors
    } else {
        MagentaLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content,
    )
}
