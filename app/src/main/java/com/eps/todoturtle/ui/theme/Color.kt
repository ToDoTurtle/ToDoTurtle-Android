package com.eps.todoturtle.ui.theme

import androidx.compose.ui.graphics.Color

val colorProvider = ColorProviderFactory(MagentaTheme).getColorProvider()

class ColorProviderFactory(private val theme: Theme) {
    fun getColorProvider(): ColorProvider {
        return when (theme) {
            is GreenishTheme -> GreenishColorProvider()
            is ColdTheme -> ColdColorProvider()
            is ColdDarkTheme -> ColdDarkColorProvider()
            is AmberTheme -> AmberColorProvider()
            is MagentaTheme -> MagentaColorProvider()
        }
    }
}

sealed class Theme
object GreenishTheme : Theme()
object ColdTheme : Theme()
object ColdDarkTheme : Theme()
object AmberTheme : Theme()
object MagentaTheme : Theme()

val md_theme_light_primary = colorProvider.lightPrimary
val md_theme_light_onPrimary = colorProvider.lightOnPrimary
val md_theme_light_primaryContainer = colorProvider.lightPrimaryContainer
val md_theme_light_onPrimaryContainer = colorProvider.lightOnPrimaryContainer
val md_theme_light_secondary = colorProvider.lightSecondary
val md_theme_light_onSecondary = colorProvider.lightOnSecondary
val md_theme_light_secondaryContainer = colorProvider.lightSecondaryContainer
val md_theme_light_onSecondaryContainer = colorProvider.lightOnSecondaryContainer
val md_theme_light_tertiary = colorProvider.lightTertiary
val md_theme_light_onTertiary = colorProvider.lightOnTertiary
val md_theme_light_tertiaryContainer = colorProvider.lightTertiaryContainer
val md_theme_light_onTertiaryContainer = colorProvider.lightOnTertiaryContainer
val md_theme_light_error = colorProvider.lightError
val md_theme_light_errorContainer = colorProvider.lightErrorContainer
val md_theme_light_onError = colorProvider.lightOnError
val md_theme_light_onErrorContainer = colorProvider.lightOnErrorContainer
val md_theme_light_background = colorProvider.lightBackground
val md_theme_light_onBackground = colorProvider.lightOnBackground
val md_theme_light_surface = colorProvider.lightSurface
val md_theme_light_onSurface = colorProvider.lightOnSurface
val md_theme_light_surfaceVariant = colorProvider.lightSurfaceVariant
val md_theme_light_onSurfaceVariant = colorProvider.lightOnSurfaceVariant
val md_theme_light_outline = colorProvider.lightOutline
val md_theme_light_inverseOnSurface = colorProvider.lightInverseOnSurface
val md_theme_light_inverseSurface = colorProvider.lightInverseSurface
val md_theme_light_inversePrimary = colorProvider.lightInversePrimary
val md_theme_light_shadow = colorProvider.lightShadow
val md_theme_light_surfaceTint = colorProvider.lightSurfaceTint
val md_theme_light_outlineVariant = colorProvider.lightOutlineVariant
val md_theme_light_scrim = colorProvider.lightScrim

val md_theme_dark_primary = colorProvider.darkPrimary
val md_theme_dark_onPrimary = colorProvider.darkOnPrimary
val md_theme_dark_primaryContainer = colorProvider.darkPrimaryContainer
val md_theme_dark_onPrimaryContainer = colorProvider.darkOnPrimaryContainer
val md_theme_dark_secondary = colorProvider.darkSecondary
val md_theme_dark_onSecondary = colorProvider.darkOnSecondary
val md_theme_dark_secondaryContainer = colorProvider.darkSecondaryContainer
val md_theme_dark_onSecondaryContainer = colorProvider.darkOnSecondaryContainer
val md_theme_dark_tertiary = colorProvider.darkTertiary
val md_theme_dark_onTertiary = colorProvider.darkOnTertiary
val md_theme_dark_tertiaryContainer = colorProvider.darkTertiaryContainer
val md_theme_dark_onTertiaryContainer = colorProvider.darkOnTertiaryContainer
val md_theme_dark_error = colorProvider.darkError
val md_theme_dark_errorContainer = colorProvider.darkErrorContainer
val md_theme_dark_onError = colorProvider.darkOnError
val md_theme_dark_onErrorContainer = colorProvider.darkOnErrorContainer
val md_theme_dark_background = colorProvider.darkBackground
val md_theme_dark_onBackground = colorProvider.darkOnBackground
val md_theme_dark_surface = colorProvider.darkSurface
val md_theme_dark_onSurface = colorProvider.darkOnSurface
val md_theme_dark_surfaceVariant = colorProvider.darkSurfaceVariant
val md_theme_dark_onSurfaceVariant = colorProvider.darkOnSurfaceVariant
val md_theme_dark_outline = colorProvider.darkOutline
val md_theme_dark_inverseOnSurface = colorProvider.darkInverseOnSurface
val md_theme_dark_inverseSurface = colorProvider.darkInverseSurface
val md_theme_dark_inversePrimary = colorProvider.darkInversePrimary
val md_theme_dark_shadow = colorProvider.darkShadow
val md_theme_dark_surfaceTint = colorProvider.darkSurfaceTint
val md_theme_dark_outlineVariant = colorProvider.darkOutlineVariant
val md_theme_dark_scrim = colorProvider.darkScrim

val seed = colorProvider.seed

interface ColorProvider {
    val lightPrimary: Color
    val lightOnPrimary: Color
    val lightPrimaryContainer: Color
    val lightOnPrimaryContainer: Color
    val lightSecondary: Color
    val lightOnSecondary: Color
    val lightSecondaryContainer: Color
    val lightOnSecondaryContainer: Color
    val lightTertiary: Color
    val lightOnTertiary: Color
    val lightTertiaryContainer: Color
    val lightOnTertiaryContainer: Color
    val lightError: Color
    val lightErrorContainer: Color
    val lightOnError: Color
    val lightOnErrorContainer: Color
    val lightBackground: Color
    val lightOnBackground: Color
    val lightSurface: Color
    val lightOnSurface: Color
    val lightSurfaceVariant: Color
    val lightOnSurfaceVariant: Color
    val lightOutline: Color
    val lightInverseOnSurface: Color
    val lightInverseSurface: Color
    val lightInversePrimary: Color
    val lightShadow: Color
    val lightSurfaceTint: Color
    val lightOutlineVariant: Color
    val lightScrim: Color

    val darkPrimary: Color
    val darkOnPrimary: Color
    val darkPrimaryContainer: Color
    val darkOnPrimaryContainer: Color
    val darkSecondary: Color
    val darkOnSecondary: Color
    val darkSecondaryContainer: Color
    val darkOnSecondaryContainer: Color
    val darkTertiary: Color
    val darkOnTertiary: Color
    val darkTertiaryContainer: Color
    val darkOnTertiaryContainer: Color
    val darkError: Color
    val darkErrorContainer: Color
    val darkOnError: Color
    val darkOnErrorContainer: Color
    val darkBackground: Color
    val darkOnBackground: Color
    val darkSurface: Color
    val darkOnSurface: Color
    val darkSurfaceVariant: Color
    val darkOnSurfaceVariant: Color
    val darkOutline: Color
    val darkInverseOnSurface: Color
    val darkInverseSurface: Color
    val darkInversePrimary: Color
    val darkShadow: Color
    val darkSurfaceTint: Color
    val darkOutlineVariant: Color
    val darkScrim: Color

    val seed: Color
}

class GreenishColorProvider : ColorProvider {
    override val lightPrimary: Color = Color(0xFF8DD88C)
    override val lightOnPrimary: Color = Color(0xFF00390D)
    override val lightPrimaryContainer: Color = Color(0xFF005317)
    override val lightOnPrimaryContainer: Color = Color(0xFFA8F5A5)
    override val lightSecondary: Color = Color(0xFFB9CCB4)
    override val lightOnSecondary: Color = Color(0xFF253423)
    override val lightSecondaryContainer: Color = Color(0xFF3B4B39)
    override val lightOnSecondaryContainer: Color = Color(0xFFD5E8CF)
    override val lightTertiary: Color = Color(0xFFA1CED5)
    override val lightOnTertiary: Color = Color(0xFF00363C)
    override val lightTertiaryContainer: Color = Color(0xFF1F4D53)
    override val lightOnTertiaryContainer: Color = Color(0xFFBCEBF1)
    override val lightError: Color = Color(0xFFFFB4AB)
    override val lightErrorContainer: Color = Color(0xFF93000A)
    override val lightOnError: Color = Color(0xFF690005)
    override val lightOnErrorContainer: Color = Color(0xFFFFDAD6)
    override val lightBackground: Color = Color(0xFF1A1C19)
    override val lightOnBackground: Color = Color(0xFFE2E3DD)
    override val lightSurface: Color = Color(0xFF1A1C19)
    override val lightOnSurface: Color = Color(0xFFE2E3DD)
    override val lightSurfaceVariant: Color = Color(0xFF424940)
    override val lightOnSurfaceVariant: Color = Color(0xFFC2C9BD)
    override val lightOutline: Color = Color(0xFF8C9388)
    override val lightInverseOnSurface: Color = Color(0xFF1A1C19)
    override val lightInverseSurface: Color = Color(0xFFE2E3DD)
    override val lightInversePrimary: Color = Color(0xFF246C2D)
    override val lightShadow: Color = Color(0xFF000000)
    override val lightSurfaceTint: Color = Color(0xFF8DD88C)
    override val lightOutlineVariant: Color = Color(0xFF424940)
    override val lightScrim: Color = Color(0xFF000000)

    override val darkPrimary: Color = Color(0xFF8DD88C)
    override val darkOnPrimary: Color = Color(0xFF00390D)
    override val darkPrimaryContainer: Color = Color(0xFF005317)
    override val darkOnPrimaryContainer: Color = Color(0xFFA8F5A5)
    override val darkSecondary: Color = Color(0xFFB9CCB4)
    override val darkOnSecondary: Color = Color(0xFF253423)
    override val darkSecondaryContainer: Color = Color(0xFF3B4B39)
    override val darkOnSecondaryContainer: Color = Color(0xFFD5E8CF)
    override val darkTertiary: Color = Color(0xFFA1CED5)
    override val darkOnTertiary: Color = Color(0xFF00363C)
    override val darkTertiaryContainer: Color = Color(0xFF1F4D53)
    override val darkOnTertiaryContainer: Color = Color(0xFFBCEBF1)
    override val darkError: Color = Color(0xFFFFB4AB)
    override val darkErrorContainer: Color = Color(0xFF93000A)
    override val darkOnError: Color = Color(0xFF690005)
    override val darkOnErrorContainer: Color = Color(0xFFFFDAD6)
    override val darkBackground: Color = Color(0xFF1A1C19)
    override val darkOnBackground: Color = Color(0xFFE2E3DD)
    override val darkSurface: Color = Color(0xFF1A1C19)
    override val darkOnSurface: Color = Color(0xFFE2E3DD)
    override val darkSurfaceVariant: Color = Color(0xFF424940)
    override val darkOnSurfaceVariant: Color = Color(0xFFC2C9BD)
    override val darkOutline: Color = Color(0xFF8C9388)
    override val darkInverseOnSurface: Color = Color(0xFF1A1C19)
    override val darkInverseSurface: Color = Color(0xFFE2E3DD)
    override val darkInversePrimary: Color = Color(0xFF246C2D)
    override val darkShadow: Color = Color(0xFF000000)
    override val darkSurfaceTint: Color = Color(0xFF8DD88C)
    override val darkOutlineVariant: Color = Color(0xFF424940)
    override val darkScrim: Color = Color(0xFF000000)

    override val seed = Color(0xFF679765)
}

class ColdColorProvider : ColorProvider {
    override val lightPrimary = Color(0xFF00696C)
    override val lightOnPrimary = Color(0xFFFFFFFF)
    override val lightPrimaryContainer = Color(0xFF6FF6FB)
    override val lightOnPrimaryContainer = Color(0xFF002021)
    override val lightSecondary = Color(0xFF4A6364)
    override val lightOnSecondary = Color(0xFFFFFFFF)
    override val lightSecondaryContainer = Color(0xFFCCE8E8)
    override val lightOnSecondaryContainer = Color(0xFF041F20)
    override val lightTertiary = Color(0xFF4D5F7C)
    override val lightOnTertiary = Color(0xFFFFFFFF)
    override val lightTertiaryContainer = Color(0xFFD4E3FF)
    override val lightOnTertiaryContainer = Color(0xFF061C36)
    override val lightError = Color(0xFFBA1A1A)
    override val lightErrorContainer = Color(0xFFFFDAD6)
    override val lightOnError = Color(0xFFFFFFFF)
    override val lightOnErrorContainer = Color(0xFF410002)
    override val lightBackground = Color(0xFFFAFDFC)
    override val lightOnBackground = Color(0xFF191C1C)
    override val lightSurface = Color(0xFFFAFDFC)
    override val lightOnSurface = Color(0xFF191C1C)
    override val lightSurfaceVariant = Color(0xFFDAE4E4)
    override val lightOnSurfaceVariant = Color(0xFF3F4949)
    override val lightOutline = Color(0xFF6F7979)
    override val lightInverseOnSurface = Color(0xFFEFF1F1)
    override val lightInverseSurface = Color(0xFF2D3131)
    override val lightInversePrimary = Color(0xFF4CD9DE)
    override val lightShadow = Color(0xFF000000)
    override val lightSurfaceTint = Color(0xFF00696C)
    override val lightOutlineVariant = Color(0xFFBEC8C8)
    override val lightScrim = Color(0xFF000000)

    override val darkPrimary = Color(0xFF4CD9DE)
    override val darkOnPrimary = Color(0xFF003738)
    override val darkPrimaryContainer = Color(0xFF004F51)
    override val darkOnPrimaryContainer = Color(0xFF6FF6FB)
    override val darkSecondary = Color(0xFFB0CCCC)
    override val darkOnSecondary = Color(0xFF1B3435)
    override val darkSecondaryContainer = Color(0xFF324B4C)
    override val darkOnSecondaryContainer = Color(0xFFCCE8E8)
    override val darkTertiary = Color(0xFFB4C7E9)
    override val darkOnTertiary = Color(0xFF1E314C)
    override val darkTertiaryContainer = Color(0xFF354863)
    override val darkOnTertiaryContainer = Color(0xFFD4E3FF)
    override val darkError = Color(0xFFFFB4AB)
    override val darkErrorContainer = Color(0xFF93000A)
    override val darkOnError = Color(0xFF690005)
    override val darkOnErrorContainer = Color(0xFFFFDAD6)
    override val darkBackground = Color(0xFF191C1C)
    override val darkOnBackground = Color(0xFFE0E3E2)
    override val darkSurface = Color(0xFF191C1C)
    override val darkOnSurface = Color(0xFFE0E3E2)
    override val darkSurfaceVariant = Color(0xFF3F4949)
    override val darkOnSurfaceVariant = Color(0xFFBEC8C8)
    override val darkOutline = Color(0xFF899393)
    override val darkInverseOnSurface = Color(0xFF191C1C)
    override val darkInverseSurface = Color(0xFFE0E3E2)
    override val darkInversePrimary = Color(0xFF00696C)
    override val darkShadow = Color(0xFF000000)
    override val darkSurfaceTint = Color(0xFF4CD9DE)
    override val darkOutlineVariant = Color(0xFF3F4949)
    override val darkScrim = Color(0xFF000000)

    override val seed = Color(0xFF6A8E8F)
}

class ColdDarkColorProvider : ColorProvider {
    override val lightPrimary = Color(0xFF006590)
    override val lightOnPrimary = Color(0xFFFFFFFF)
    override val lightPrimaryContainer = Color(0xFFC8E6FF)
    override val lightOnPrimaryContainer = Color(0xFF001E2E)
    override val lightSecondary = Color(0xFF4F606E)
    override val lightOnSecondary = Color(0xFFFFFFFF)
    override val lightSecondaryContainer = Color(0xFFD3E5F5)
    override val lightOnSecondaryContainer = Color(0xFF0B1D29)
    override val lightTertiary = Color(0xFF63597C)
    override val lightOnTertiary = Color(0xFFFFFFFF)
    override val lightTertiaryContainer = Color(0xFFE9DDFF)
    override val lightOnTertiaryContainer = Color(0xFF1F1635)
    override val lightError = Color(0xFFBA1A1A)
    override val lightErrorContainer = Color(0xFFFFDAD6)
    override val lightOnError = Color(0xFFFFFFFF)
    override val lightOnErrorContainer = Color(0xFF410002)
    override val lightBackground = Color(0xFFFCFCFF)
    override val lightOnBackground = Color(0xFF191C1E)
    override val lightSurface = Color(0xFFFCFCFF)
    override val lightOnSurface = Color(0xFF191C1E)
    override val lightSurfaceVariant = Color(0xFFDDE3EA)
    override val lightOnSurfaceVariant = Color(0xFF41484D)
    override val lightOutline = Color(0xFF71787E)
    override val lightInverseOnSurface = Color(0xFFF0F0F3)
    override val lightInverseSurface = Color(0xFF2E3133)
    override val lightInversePrimary = Color(0xFF87CEFF)
    override val lightShadow = Color(0xFF000000)
    override val lightSurfaceTint = Color(0xFF006590)
    override val lightOutlineVariant = Color(0xFFC1C7CE)
    override val lightScrim = Color(0xFF000000)

    override val darkPrimary = Color(0xFF87CEFF)
    override val darkOnPrimary = Color(0xFF00344D)
    override val darkPrimaryContainer = Color(0xFF004C6D)
    override val darkOnPrimaryContainer = Color(0xFFC8E6FF)
    override val darkSecondary = Color(0xFFB7C9D8)
    override val darkOnSecondary = Color(0xFF21323F)
    override val darkSecondaryContainer = Color(0xFF384956)
    override val darkOnSecondaryContainer = Color(0xFFD3E5F5)
    override val darkTertiary = Color(0xFFCEC0E8)
    override val darkOnTertiary = Color(0xFF342B4B)
    override val darkTertiaryContainer = Color(0xFF4B4163)
    override val darkOnTertiaryContainer = Color(0xFFE9DDFF)
    override val darkError = Color(0xFFFFB4AB)
    override val darkErrorContainer = Color(0xFF93000A)
    override val darkOnError = Color(0xFF690005)
    override val darkOnErrorContainer = Color(0xFFFFDAD6)
    override val darkBackground = Color(0xFF191C1E)
    override val darkOnBackground = Color(0xFFE2E2E5)
    override val darkSurface = Color(0xFF191C1E)
    override val darkOnSurface = Color(0xFFE2E2E5)
    override val darkSurfaceVariant = Color(0xFF41484D)
    override val darkOnSurfaceVariant = Color(0xFFC1C7CE)
    override val darkOutline = Color(0xFF8B9198)
    override val darkInverseOnSurface = Color(0xFF191C1E)
    override val darkInverseSurface = Color(0xFFE2E2E5)
    override val darkInversePrimary = Color(0xFF006590)
    override val darkShadow = Color(0xFFE2E2E5)
    override val darkSurfaceTint = Color(0xFF87CEFF)
    override val darkOutlineVariant = Color(0xFF41484D)
    override val darkScrim = Color(0xFF000000)

    override val seed = Color(0xFF395E77)
}

class AmberColorProvider : ColorProvider {
    override val lightPrimary = Color(0xFF815500)
    override val lightOnPrimary = Color(0xFFFFFFFF)
    override val lightPrimaryContainer = Color(0xFFFFDDB2)
    override val lightOnPrimaryContainer = Color(0xFF291800)
    override val lightSecondary = Color(0xFF6F5B40)
    override val lightOnSecondary = Color(0xFFFFFFFF)
    override val lightSecondaryContainer = Color(0xFFFADEBC)
    override val lightOnSecondaryContainer = Color(0xFF271904)
    override val lightTertiary = Color(0xFF516440)
    override val lightOnTertiary = Color(0xFFFFFFFF)
    override val lightTertiaryContainer = Color(0xFFD3EABC)
    override val lightOnTertiaryContainer = Color(0xFF0F2004)
    override val lightError = Color(0xFFBA1A1A)
    override val lightErrorContainer = Color(0xFFFFDAD6)
    override val lightOnError = Color(0xFFFFFFFF)
    override val lightOnErrorContainer = Color(0xFF410002)
    override val lightBackground = Color(0xFFFFFBFF)
    override val lightOnBackground = Color(0xFF1F1B16)
    override val lightSurface = Color(0xFFFFFBFF)
    override val lightOnSurface = Color(0xFF1F1B16)
    override val lightSurfaceVariant = Color(0xFFF0E0CF)
    override val lightOnSurfaceVariant = Color(0xFF4F4539)
    override val lightOutline = Color(0xFF817567)
    override val lightInverseOnSurface = Color(0xFFF9EFE7)
    override val lightInverseSurface = Color(0xFF34302A)
    override val lightInversePrimary = Color(0xFFFFB94D)
    override val lightShadow = Color(0xFF000000)
    override val lightSurfaceTint = Color(0xFF815500)
    override val lightOutlineVariant = Color(0xFFD3C4B4)
    override val lightScrim = Color(0xFF000000)

    override val darkPrimary = Color(0xFFFFB94D)
    override val darkOnPrimary = Color(0xFF442B00)
    override val darkPrimaryContainer = Color(0xFF624000)
    override val darkOnPrimaryContainer = Color(0xFFFFDDB2)
    override val darkSecondary = Color(0xFFDDC2A1)
    override val darkOnSecondary = Color(0xFF3E2E16)
    override val darkSecondaryContainer = Color(0xFF56442A)
    override val darkOnSecondaryContainer = Color(0xFFFADEBC)
    override val darkTertiary = Color(0xFFB8CEA1)
    override val darkOnTertiary = Color(0xFF243516)
    override val darkTertiaryContainer = Color(0xFF3A4C2A)
    override val darkOnTertiaryContainer = Color(0xFFD3EABC)
    override val darkError = Color(0xFFFFB4AB)
    override val darkErrorContainer = Color(0xFF93000A)
    override val darkOnError = Color(0xFF690005)
    override val darkOnErrorContainer = Color(0xFFFFDAD6)
    override val darkBackground = Color(0xFF1F1B16)
    override val darkOnBackground = Color(0xFFEAE1D9)
    override val darkSurface = Color(0xFF1F1B16)
    override val darkOnSurface = Color(0xFFEAE1D9)
    override val darkSurfaceVariant = Color(0xFF4F4539)
    override val darkOnSurfaceVariant = Color(0xFFD3C4B4)
    override val darkOutline = Color(0xFF9B8F80)
    override val darkInverseOnSurface = Color(0xFF1F1B16)
    override val darkInverseSurface = Color(0xFFEAE1D9)
    override val darkInversePrimary = Color(0xFF815500)
    override val darkShadow = Color(0xFF000000)
    override val darkSurfaceTint = Color(0xFFFFB94D)
    override val darkOutlineVariant = Color(0xFF4F4539)
    override val darkScrim = Color(0xFF000000)

    override val seed = Color(0xFFD69E49)
}

class MagentaColorProvider : ColorProvider {
    override val lightPrimary = Color(0xFF914278)
    override val lightOnPrimary = Color(0xFFFFFFFF)
    override val lightPrimaryContainer = Color(0xFFFFD8EC)
    override val lightOnPrimaryContainer = Color(0xFF3B002E)
    override val lightSecondary = Color(0xFF705765)
    override val lightOnSecondary = Color(0xFFFFFFFF)
    override val lightSecondaryContainer = Color(0xFFFBD9EA)
    override val lightOnSecondaryContainer = Color(0xFF281621)
    override val lightTertiary = Color(0xFF80543E)
    override val lightOnTertiary = Color(0xFFFFFFFF)
    override val lightTertiaryContainer = Color(0xFFFFDBCC)
    override val lightOnTertiaryContainer = Color(0xFF311303)
    override val lightError = Color(0xFFBA1A1A)
    override val lightErrorContainer = Color(0xFFFFDAD6)
    override val lightOnError = Color(0xFFFFFFFF)
    override val lightOnErrorContainer = Color(0xFF410002)
    override val lightBackground = Color(0xFFFFFBFF)
    override val lightOnBackground = Color(0xFF1F1A1D)
    override val lightSurface = Color(0xFFFFFBFF)
    override val lightOnSurface = Color(0xFF1F1A1D)
    override val lightSurfaceVariant = Color(0xFFF0DEE5)
    override val lightOnSurfaceVariant = Color(0xFF4F4449)
    override val lightOutline = Color(0xFF81737A)
    override val lightInverseOnSurface = Color(0xFFF9EEF1)
    override val lightInverseSurface = Color(0xFF342F31)
    override val lightInversePrimary = Color(0xFFFFAEDF)
    override val lightShadow = Color(0xFF000000)
    override val lightSurfaceTint = Color(0xFF914278)
    override val lightOutlineVariant = Color(0xFFD3C2C9)
    override val lightScrim = Color(0xFF000000)

    override val darkPrimary = Color(0xFFFFAEDF)
    override val darkOnPrimary = Color(0xFF591147)
    override val darkPrimaryContainer = Color(0xFF752A5F)
    override val darkOnPrimaryContainer = Color(0xFFFFD8EC)
    override val darkSecondary = Color(0xFFDEBECE)
    override val darkOnSecondary = Color(0xFF3F2A37)
    override val darkSecondaryContainer = Color(0xFF57404D)
    override val darkOnSecondaryContainer = Color(0xFFFBD9EA)
    override val darkTertiary = Color(0xFFF4BA9F)
    override val darkOnTertiary = Color(0xFF4B2714)
    override val darkTertiaryContainer = Color(0xFF653D28)
    override val darkOnTertiaryContainer = Color(0xFFFFDBCC)
    override val darkError = Color(0xFFFFB4AB)
    override val darkErrorContainer = Color(0xFF93000A)
    override val darkOnError = Color(0xFF690005)
    override val darkOnErrorContainer = Color(0xFFFFDAD6)
    override val darkBackground = Color(0xFF1F1A1D)
    override val darkOnBackground = Color(0xFFEAE0E3)
    override val darkSurface = Color(0xFF1F1A1D)
    override val darkOnSurface = Color(0xFFEAE0E3)
    override val darkSurfaceVariant = Color(0xFF4F4449)
    override val darkOnSurfaceVariant = Color(0xFFD3C2C9)
    override val darkOutline = Color(0xFF9C8D93)
    override val darkInverseOnSurface = Color(0xFF1F1A1D)
    override val darkInverseSurface = Color(0xFFEAE0E3)
    override val darkInversePrimary = Color(0xFF914278)
    override val darkShadow = Color(0xFF000000)
    override val darkSurfaceTint = Color(0xFFFFAEDF)
    override val darkOutlineVariant = Color(0xFF4F4449)
    override val darkScrim = Color(0xFF000000)

    override val seed = Color(0xFF893F71)
}
