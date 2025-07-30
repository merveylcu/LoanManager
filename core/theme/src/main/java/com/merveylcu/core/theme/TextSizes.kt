package com.merveylcu.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class AppTextSizes(
    val extraSmall: TextUnit = 10.sp,
    val small: TextUnit = 12.sp,
    val medium: TextUnit = 14.sp,
    val large: TextUnit = 16.sp,
    val extraLarge: TextUnit = 20.sp,
    val title: TextUnit = 24.sp,
    val headline: TextUnit = 32.sp
)

val CompactTextSizes = AppTextSizes(
    extraSmall = 8.sp,
    small = 10.sp,
    medium = 12.sp,
    large = 14.sp,
    extraLarge = 18.sp,
    title = 20.sp,
    headline = 28.sp
)

val RegularTextSizes = AppTextSizes()

val LocalAppTextSizes = staticCompositionLocalOf { RegularTextSizes }
