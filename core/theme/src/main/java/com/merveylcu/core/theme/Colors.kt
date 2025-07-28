package com.merveylcu.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val textPrimary: Color,
    val error: Color
)

val LightColors = AppColors(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFFFFFFF),
    textPrimary = Color(0xFF000000),
    error = Color(0xFFB00020)
)

val DarkColors = AppColors(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    textPrimary = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679)
)

val LocalAppColors = staticCompositionLocalOf { LightColors }
