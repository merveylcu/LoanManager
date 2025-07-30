package com.merveylcu.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 16.dp,
    val cornerRadiusSmall: Dp = 4.dp,
    val cornerRadiusMedium: Dp = 8.dp,
    val cornerRadiusLarge: Dp = 16.dp,
    val spacingVertical: Dp = 12.dp,
    val spacingHorizontal: Dp = 12.dp,
    val iconSize: Dp = 24.dp,
    val elevation: Dp = 2.dp,
    val textFieldHeight: Dp = 58.dp,
    val buttonHeight: Dp = 52.dp,
    val borderWidth: Dp = 0.6.dp
)

val CompactDimensions = AppDimensions(
    paddingSmall = 2.dp,
    paddingMedium = 6.dp,
    paddingLarge = 12.dp,
    cornerRadiusSmall = 2.dp,
    cornerRadiusMedium = 6.dp,
    cornerRadiusLarge = 12.dp,
    spacingVertical = 8.dp,
    spacingHorizontal = 8.dp,
    iconSize = 20.dp,
    elevation = 1.dp,
    textFieldHeight = 42.dp,
    buttonHeight = 36.dp,
    borderWidth = 0.3.dp
)

val RegularDimensions = AppDimensions()

val LocalAppDimensions = staticCompositionLocalOf { RegularDimensions }
