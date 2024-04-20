package ru.tinkoff.tinkoffer.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFDD2D),
    onPrimary = Color(0xCC000000),

    background = Color(0xFF212121),
    onBackground = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFDD2D),
    onPrimary = Color(0xCC000000),

    background = Color(0xFFF6F7F8),
    onBackground = Color.Black,

    secondary = Color(0xFF548BF7),
    onSecondary = Color.Black,

    surfaceVariant = Color.White,
    onSurfaceVariant = Color(0xFF212121),

    surface = Color.White,
    onSurface = Color.Black,

    primaryContainer = Color(0xFFECECEC),
    onPrimaryContainer = Color.Black,

    secondaryContainer = Color(0xFFECECEC),
    onSecondaryContainer = Color(0xFFECECEC),
)

@Composable
fun AppTheme(
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
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}