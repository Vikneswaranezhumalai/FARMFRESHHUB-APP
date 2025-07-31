package com.txstate.sampleapicall.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val lightScheme = lightColorScheme(
    primary = Color(0xFF388E3C),            // Rich green (FarmFresh primary)
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),   // Light green container
    onPrimaryContainer = Color(0xFF002D06),
    secondary = Color(0xFF8BC34A),          // Lighter green
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCE775), // Pastel yellow-green
    onSecondaryContainer = Color(0xFF263300),
    tertiary = Color(0xFFFFB300),           // Farm yellow
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE082),
    onTertiaryContainer = Color(0xFF332600),
    error = Color(0xFFD32F2F),
    onError = Color.White,
    errorContainer = Color(0xFFFFCDD2),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF8FFF6),         // Gentle light background
    onBackground = Color(0xFF222D20),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF222D20),
    surfaceVariant = Color(0xFFE0F2F1),
    onSurfaceVariant = Color(0xFF43534B),
    outline = Color(0xFFB5CDBA),
    outlineVariant = Color(0xFFD9EDE1),
    scrim = Color(0x66000000),
    inverseSurface = Color(0xFF222D20),
    inverseOnSurface = Color(0xFFF8FFF6),
    inversePrimary = Color(0xFF66BB6A),
    surfaceDim = Color(0xFFF0F5F0),
    surfaceBright = Color(0xFFFFFFFF),
    surfaceContainerLowest = Color(0xFFF8FFF6),
    surfaceContainerLow = Color(0xFFF3FBF1),
    surfaceContainer = Color(0xFFF0F5F0),
    surfaceContainerHigh = Color(0xFFE5EDE6),
    surfaceContainerHighest = Color(0xFFE0F2F1),
)


private val darkScheme = darkColorScheme(
    primary = Color(0xFF81C784),            // Lighter green for dark mode
    onPrimary = Color(0xFF003911),
    primaryContainer = Color(0xFF388E3C),
    onPrimaryContainer = Color(0xFFC8E6C9),
    secondary = Color(0xFFAED581),          // Soft green
    onSecondary = Color(0xFF1A3300),
    secondaryContainer = Color(0xFF8BC34A),
    onSecondaryContainer = Color(0xFFDCE775),
    tertiary = Color(0xFFFFE082),
    onTertiary = Color(0xFF665100),
    tertiaryContainer = Color(0xFFFFB300),
    onTertiaryContainer = Color(0xFFFFE082),
    error = Color(0xFFFF8A80),
    onError = Color(0xFF5B0000),
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = Color(0xFFFFCDD2),
    background = Color(0xFF111C13),         // Deep green/black
    onBackground = Color(0xFFE8F5E9),
    surface = Color(0xFF191F1D),
    onSurface = Color(0xFFD3EAD4),
    surfaceVariant = Color(0xFF26352A),
    onSurfaceVariant = Color(0xFFB5CDBA),
    outline = Color(0xFF43534B),
    outlineVariant = Color(0xFF222D20),
    scrim = Color(0xAA000000),
    inverseSurface = Color(0xFFE8F5E9),
    inverseOnSurface = Color(0xFF222D20),
    inversePrimary = Color(0xFFC8E6C9),
    surfaceDim = Color(0xFF191F1D),
    surfaceBright = Color(0xFF26352A),
    surfaceContainerLowest = Color(0xFF111C13),
    surfaceContainerLow = Color(0xFF191F1D),
    surfaceContainer = Color(0xFF222D20),
    surfaceContainerHigh = Color(0xFF26352A),
    surfaceContainerHighest = Color(0xFF388E3C),
)


@Composable
fun FarmFreshApiCallTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}
