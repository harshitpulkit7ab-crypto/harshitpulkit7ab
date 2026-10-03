package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class MonetPalette(val displayName: String, val primary: Color, val container: Color) {
  PixelBlue("Pixel Blue", Color(0xFFA8C7FA), Color(0xFF0842A0)),
  PixelMint("Mint Green", Color(0xFF80E2B8), Color(0xFF005139)),
  PixelAmber("Desert Amber", Color(0xFFFFB74D), Color(0xFF5D2E00)),
  PixelBerry("Berry Rose", Color(0xFFFFB2B7), Color(0xFF5B111B)),
  PixelSky("Sky Azure", Color(0xFF70D2FF), Color(0xFF004D69)),
  PixelObsidian("Obsidian Gray", Color(0xFFD4D4D8), Color(0xFF27272A))
}

fun getPixelDarkColorScheme(palette: MonetPalette): ColorScheme {
  return darkColorScheme(
    primary = palette.primary,
    onPrimary = Color(0xFF001F2B),
    primaryContainer = palette.container,
    onPrimaryContainer = Color(0xFFCBE6FF),
    secondary = palette.primary.copy(alpha = 0.85f),
    onSecondary = Color(0xFF001F2B),
    secondaryContainer = palette.container.copy(alpha = 0.7f),
    onSecondaryContainer = Color(0xFFE2E2E9),
    tertiary = PixelMint,
    background = PixelDarkBackground,
    onBackground = PixelDarkOnSurface,
    surface = PixelDarkSurface,
    onSurface = PixelDarkOnSurface,
    surfaceVariant = PixelDarkSurfaceVariant,
    onSurfaceVariant = PixelDarkOnSurfaceVariant,
    surfaceContainer = PixelDarkSurfaceContainer,
    surfaceContainerHigh = PixelDarkSurfaceContainerHigh,
    outline = Color(0xFF49454F),
    outlineVariant = Color(0xFF33353B)
  )
}

fun getPixelLightColorScheme(palette: MonetPalette): ColorScheme {
  return lightColorScheme(
    primary = palette.container,
    onPrimary = Color.White,
    primaryContainer = palette.primary,
    onPrimaryContainer = Color(0xFF001D33),
    secondary = palette.container.copy(alpha = 0.8f),
    onSecondary = Color.White,
    background = PixelLightBackground,
    onBackground = PixelLightOnSurface,
    surface = PixelLightSurface,
    onSurface = PixelLightOnSurface,
    surfaceVariant = PixelLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF444746)
  )
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  palette: MonetPalette = MonetPalette.PixelBlue,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) getPixelDarkColorScheme(palette) else getPixelLightColorScheme(palette)
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

