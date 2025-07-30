package com.merveylcu.feature.loan.presentation

import androidx.lifecycle.viewModelScope
import com.merveylcu.core.common.base.BaseViewModel
import com.merveylcu.core.common.navigation.LoginNavigation
import com.merveylcu.core.common.sharedpref.LoanPreferences
import com.merveylcu.feature.loan.domain.GetLoansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    private val getLoans: GetLoansUseCase,
    private val loanPreferences: LoanPreferences,
    private val loginNavigation: LoginNavigation
) : BaseViewModel(), LoanActions {

    private val _uiState = MutableStateFlow(LoanUiState())
    val uiState: StateFlow<LoanUiState> = _uiState.asStateFlow()

    fun initPage() {
        viewModelScope.launch {
            try {
                _uiState.update { state ->
                    state.copy(loans = getLoans.invoke())
                }
            } catch (e: Exception) {
                print(e)
            }
        }
    }

    override fun onLogout() {
        loanPreferences.clear()
        loginNavigation.navigateToLogin()
    }
}
