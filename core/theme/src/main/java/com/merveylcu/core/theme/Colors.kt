package com.merveylcu.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val textPrimary: Color,
    val error: Color,
    val passive: Color,
    val active: Color,
    val transparent: Color
)

val LightColors = AppColors(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFFFFFFF),
    textPrimary = Color(0xFF000000),
    error = Color(0xFFB00020),
    passive = Color(0xFF9E9E9E),
    active = Color(0xFF6200EE),
    transparent = Color(0x00000000)
)

val DarkColors = AppColors(
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    textPrimary = Color(0xFFFFFFFF),
    error = Color(0xFFCF6679),
    passive = Color(0xFF757575),
    active = Color(0xFFBB86FC),
    transparent = Color(0x00000000)
)

val LocalAppColors = staticCompositionLocalOf { LightColors }
