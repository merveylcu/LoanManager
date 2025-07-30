package com.merveylcu.feature.login.presentation

import com.merveylcu.component.textfield.LoanTextFieldValue
import com.merveylcu.core.common.base.BaseViewModel
import com.merveylcu.core.common.sharedpref.LoanPreferences
import com.merveylcu.core.common.sharedpref.LoanPreferencesKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loanPreferences: LoanPreferences
) : BaseViewModel(), LoginActions {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    override fun onEmailChanged(value: LoanTextFieldValue) {
        _uiState.update { state ->
            state.copy(email = value)
        }
        updateLoginButtonIsEnabled()
    }

    override fun onPasswordChanged(value: LoanTextFieldValue) {
        _uiState.update { state ->
            state.copy(password = value)
        }
        updateLoginButtonIsEnabled()
    }

    override fun onLogin() {
        _uiState.value.apply {
            loanPreferences.putString(LoanPreferencesKey.Email, email.text)
            loanPreferences.putString(LoanPreferencesKey.Password, password.text)
        }
        // navigate
    }

    private fun updateLoginButtonIsEnabled() {
        val result = _uiState.value.email.isError.not() && _uiState.value.password.isError.not()
        _uiState.update { state ->
            state.copy(isLoginButtonEnabled = result)
        }
    }
}
