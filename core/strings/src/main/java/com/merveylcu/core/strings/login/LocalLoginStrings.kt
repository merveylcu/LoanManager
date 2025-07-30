package com.merveylcu.core.strings.login

import androidx.compose.runtime.staticCompositionLocalOf

val LocalLoginStrings = staticCompositionLocalOf<LoginStrings> {
    error("LoginStrings not provided")
}
