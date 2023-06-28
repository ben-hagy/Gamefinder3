package com.benhagy.gamefinder3.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun Gamefinder3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = gf3_dark_primary,
    onPrimary = gf3_dark_onPrimary,
    primaryContainer = gf3_dark_primaryContainer,
    onPrimaryContainer = gf3_dark_onPrimaryContainer,
    secondary = gf3_dark_secondary,
    onSecondary = gf3_dark_onSecondary,
    secondaryContainer = gf3_dark_secondaryContainer,
    onSecondaryContainer = gf3_dark_onSecondaryContainer,
    tertiary = gf3_dark_tertiary,
    onTertiary = gf3_dark_onTertiary,
    tertiaryContainer = gf3_dark_tertiaryContainer,
    onTertiaryContainer = gf3_dark_onTertiaryContainer,
    error = gf3_dark_error,
    errorContainer = gf3_dark_errorContainer,
    onError = gf3_dark_onError,
    onErrorContainer = gf3_dark_onErrorContainer,
    background = gf3_dark_background,
    onBackground = gf3_dark_onBackground,
    surface = gf3_dark_surface,
    onSurface = gf3_dark_onSurface,
    surfaceVariant = gf3_dark_surfaceVariant,
    onSurfaceVariant = gf3_dark_onSurfaceVariant,
    outline = gf3_dark_outline,
    inverseOnSurface = gf3_dark_inverseOnSurface,
    inverseSurface = gf3_dark_inverseSurface,
    inversePrimary = gf3_dark_inversePrimary,
    surfaceTint = gf3_dark_surfaceTint,
    outlineVariant = gf3_dark_outlineVariant,
    scrim = gf3_dark_scrim,
)

private val LightColorScheme = lightColorScheme(
    primary = gf3_light_primary,
    onPrimary = gf3_light_onPrimary,
    primaryContainer = gf3_light_primaryContainer,
    onPrimaryContainer = gf3_light_onPrimaryContainer,
    secondary = gf3_light_secondary,
    onSecondary = gf3_light_onSecondary,
    secondaryContainer = gf3_light_secondaryContainer,
    onSecondaryContainer = gf3_light_onSecondaryContainer,
    tertiary = gf3_light_tertiary,
    onTertiary = gf3_light_onTertiary,
    tertiaryContainer = gf3_light_tertiaryContainer,
    onTertiaryContainer = gf3_light_onTertiaryContainer,
    error = gf3_light_error,
    errorContainer = gf3_light_errorContainer,
    onError = gf3_light_onError,
    onErrorContainer = gf3_light_onErrorContainer,
    background = gf3_light_background,
    onBackground = gf3_light_onBackground,
    surface = gf3_light_surface,
    onSurface = gf3_light_onSurface,
    surfaceVariant = gf3_light_surfaceVariant,
    onSurfaceVariant = gf3_light_onSurfaceVariant,
    outline = gf3_light_outline,
    inverseOnSurface = gf3_light_inverseOnSurface,
    inverseSurface = gf3_light_inverseSurface,
    inversePrimary = gf3_light_inversePrimary,
    surfaceTint = gf3_light_surfaceTint,
    outlineVariant = gf3_light_outlineVariant,
    scrim = gf3_light_scrim,
)