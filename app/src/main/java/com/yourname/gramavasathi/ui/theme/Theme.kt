package com.yourname.gramavasathi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ForestGreen = Color(0xFF4A7C59)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A7C59),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEAF3DE),
    onPrimaryContainer = Color(0xFF2D5C3A),
    secondary = Color(0xFFD4A017),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFAEEDA),
    onSecondaryContainer = Color(0xFF854F0B),
    background = Color(0xFFFAF7F2),
    onBackground = Color(0xFF2C2C2A),
    surface = Color.White,
    onSurface = Color(0xFF2C2C2A),
    surfaceVariant = Color(0xFFF1EFE8),
    onSurfaceVariant = Color(0xFF5F5E5A),
    outline = Color(0xFFE2DDD5),
    error = Color(0xFFD85A30),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8BA888),
    onPrimary = Color(0xFF1B371E),
    primaryContainer = Color(0xFF2D5C3A),
    onPrimaryContainer = Color(0xFFEAF3DE),
    secondary = Color(0xFFFAC775),
    onSecondary = Color(0xFF452B00),
    secondaryContainer = Color(0xFF633F00),
    onSecondaryContainer = Color(0xFFFAEEDA),
    background = Color(0xFF1C1B17),
    onBackground = Color(0xFFE6E2DA),
    surface = Color(0xFF1C1B17),
    onSurface = Color(0xFFE6E2DA),
    surfaceVariant = Color(0xFF49473F),
    onSurfaceVariant = Color(0xFFCAC6BB),
    outline = Color(0xFF949186),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun GramaVasathiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
