package com.merveylcu.feature.login.presentation

import com.merveylcu.component.textfield.LoanTextFieldValue
import com.merveylcu.core.common.navigation.LoanNavigation
import com.merveylcu.core.common.sharedpref.LoanPreferences
import com.merveylcu.core.common.sharedpref.LoanPreferencesKey
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loanPreferences: LoanPreferences
    private lateinit var loanNavigation: LoanNavigation
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loanPreferences = mockk(relaxed = true)
        loanNavigation = mockk(relaxed = true)

        viewModel = LoginViewModel(
            loanPreferences = loanPreferences,
            loanNavigation = loanNavigation
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onEmailChanged updates state and triggers button validation`() = runTest {
        val emailValue = LoanTextFieldValue(text = "merve@email.com", isError = false)

        viewModel.onEmailChanged(emailValue)
        val state = viewModel.uiState.first()

        assertEquals(emailValue, state.email)
        assertTrue(state.isLoginButtonEnabled)
    }

    @Test
    fun `onPasswordChanged updates state and triggers button validation`() = runTest {
        val passwordValue = LoanTextFieldValue(text = "123456", isError = false)

        viewModel.onPasswordChanged(passwordValue)
        val state = viewModel.uiState.first()

        assertEquals(passwordValue, state.password)
        assertTrue(state.isLoginButtonEnabled)
    }

    @Test
    fun `onLogin saves credentials and navigates`() = runTest {
        val emailValue = LoanTextFieldValue("merve@mail.com", isError = false)
        val passwordValue = LoanTextFieldValue("password123", isError = false)

        viewModel.onEmailChanged(emailValue)
        viewModel.onPasswordChanged(passwordValue)

        viewModel.onLogin()

        verify {
            loanPreferences.putString(LoanPreferencesKey.Email, "merve@mail.com")
            loanPreferences.putString(LoanPreferencesKey.Password, "password123")
            loanNavigation.navigateToLoans()
        }
    }
}
