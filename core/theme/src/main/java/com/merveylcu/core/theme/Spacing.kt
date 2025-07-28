package com.merveylcu.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp

data class AppSpacing(
    val xs: Dp = 4.dp,
    val sm: Dp = 8.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 24.dp,
    val xl: Dp = 32.dp
)

val DefaultSpacing = AppSpacing()
val CompactSpacing = AppSpacing(xs = 2.dp, sm = 4.dp, md = 8.dp, lg = 12.dp, xl = 16.dp)

val LocalAppSpacing = staticCompositionLocalOf { DefaultSpacing }
