package com.merveylcu.feature.loan.presentation

import com.merveylcu.core.common.navigation.LoginNavigation
import com.merveylcu.core.common.sharedpref.LoanPreferences
import com.merveylcu.feature.loan.domain.GetLoansUseCase
import com.merveylcu.feature.loan.domain.model.LoanDetail
import com.merveylcu.feature.loan.domain.model.LoanType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoanViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getLoansUseCase: GetLoansUseCase
    private lateinit var loanPreferences: LoanPreferences
    private lateinit var loginNavigation: LoginNavigation
    private lateinit var viewModel: LoanViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getLoansUseCase = mockk()
        loanPreferences = mockk(relaxed = true)
        loginNavigation = mockk(relaxed = true)

        viewModel = LoanViewModel(
            getLoans = getLoansUseCase,
            loanPreferences = loanPreferences,
            loginNavigation = loginNavigation
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initPage should update uiState with loans`() = runTest {
        // Given
        val dummyLoans = listOf(
            LoanDetail(
                id = "1",
                type = LoanType.PERSONAL,
                amount = 10000.0,
                interestRate = 1.0,
                durationMonths = 12
            ),
            LoanDetail(
                id = "2",
                type = LoanType.AUTO,
                amount = 20000.0,
                interestRate = 1.0,
                durationMonths = 12
            )
        )
        coEvery { getLoansUseCase.invoke() } returns dummyLoans

        // When
        viewModel.initPage()
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(dummyLoans, state.loans)
        coVerify { getLoansUseCase.invoke() }
    }

    @Test
    fun `onLogout clears preferences and navigates to login`() {
        // When
        viewModel.onLogout()

        // Then
        verify { loanPreferences.clear() }
        verify { loginNavigation.navigateToLogin() }
    }
}
