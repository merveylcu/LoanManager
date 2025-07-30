package com.merveylcu.component.button

import androidx.compose.runtime.Stable

@Stable
sealed interface LoanButtonStyle {

    @Stable
    data object Primary : LoanButtonStyle

    @Stable
    data object Secondary : LoanButtonStyle
}
