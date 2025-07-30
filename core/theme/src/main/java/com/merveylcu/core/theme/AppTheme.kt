package com.merveylcu.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    isCompact: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors
    val dimensions = if (isCompact) CompactDimensions else RegularDimensions
    val spacing = if (isCompact) CompactSpacing else DefaultSpacing
    val textSizes = if (isCompact) CompactTextSizes else RegularTextSizes

    val materialColorScheme = if (useDarkTheme) {
        darkColorScheme(
            primary = colors.primary,
            secondary = colors.secondary,
            background = colors.background
        )
    } else {
        lightColorScheme(
            primary = colors.primary,
            secondary = colors.secondary,
            background = colors.background
        )
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppDimensions provides dimensions,
        LocalAppSpacing provides spacing,
        LocalAppShapes provides AppShapes,
        LocalAppTextSizes provides textSizes,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            shapes = AppShapes,
            content = content
        )
    }
}
