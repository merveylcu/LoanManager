package com.merveylcu.feature.login.presentation

import com.merveylcu.component.textfield.LoanTextFieldValue

internal interface LoginActions {

    fun onEmailChanged(value: LoanTextFieldValue)

    fun onPasswordChanged(value: LoanTextFieldValue)

    fun onLogin()
}