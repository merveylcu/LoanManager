package com.merveylcu.feature.login.presentation

import com.merveylcu.component.textfield.LoanTextFieldValue

internal data class LoginUiState(
    val email: LoanTextFieldValue = LoanTextFieldValue(),
    val password: LoanTextFieldValue = LoanTextFieldValue(),
    val isLoginButtonEnabled: Boolean = false
)
