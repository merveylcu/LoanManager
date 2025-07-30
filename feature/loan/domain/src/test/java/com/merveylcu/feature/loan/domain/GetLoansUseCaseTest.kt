package com.merveylcu.feature.loan.domain

import com.merveylcu.feature.loan.domain.model.Loan
import com.merveylcu.feature.loan.domain.model.LoanType
import com.merveylcu.feature.loan.domain.strategy.AutoLoanStrategy
import com.merveylcu.feature.loan.domain.strategy.LoanCalculationManager
import com.merveylcu.feature.loan.domain.strategy.MortgageLoanStrategy
import com.merveylcu.feature.loan.domain.strategy.PersonalLoanStrategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetLoansUseCaseTest {

    private val personalLoan = Loan("1", LoanType.PERSONAL, 10000.0)
    private val autoLoanLow = Loan("2", LoanType.AUTO, 40000.0)  // Should be 10% interest
    private val autoLoanHigh = Loan("3", LoanType.AUTO, 60000.0) // Should be 8.5% interest
    private val mortgageLoan = Loan("4", LoanType.MORTGAGE, 200000.0)

    private val fakeRepository = object : LoanRepository {
        override suspend fun getLoans(): List<Loan> {
            return listOf(personalLoan, autoLoanLow, autoLoanHigh, mortgageLoan)
        }
    }

    private val strategyMap = mapOf(
        LoanType.PERSONAL to PersonalLoanStrategy(),
        LoanType.AUTO to AutoLoanStrategy(),
        LoanType.MORTGAGE to MortgageLoanStrategy()
    )

    private val calculationManager = LoanCalculationManager(strategyMap)
    private val useCase = GetLoansUseCase(fakeRepository, calculationManager)

    @Test
    fun `returns correct interest and duration based on loan type strategy`() = runTest {
        val result = useCase()

        assertEquals(4, result.size)

        val expected = mapOf(
            "1" to Pair(14.0, 24),    // PERSONAL
            "2" to Pair(10.0, 36),    // AUTO, amount < 50k
            "3" to Pair(8.5, 36),     // AUTO, amount > 50k
            "4" to Pair(6.0, 120)     // MORTGAGE
        )

        for (loanDetail in result) {
            val expectedValues = requireNotNull(expected[loanDetail.id]) {
                "Unexpected loan ID in result: ${loanDetail.id}"
            }
            val (expectedInterest, expectedDuration) = expectedValues

            assertEquals(
                "Incorrect interest rate for loan ID ${loanDetail.id}",
                expectedInterest,
                loanDetail.interestRate,
                0.01
            )

            assertEquals(
                "Incorrect duration for loan ID ${loanDetail.id}",
                expectedDuration,
                loanDetail.durationMonths
            )
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throws exception if strategy not found for loan type`() = runTest {
        val unknownLoanType =
            LoanType.valueOf("BUSINESS") // Only if BUSINESS is not in enum, this will fail

        val loanWithMissingStrategy = Loan("99", unknownLoanType, 50000.0)

        val failingRepository = object : LoanRepository {
            override suspend fun getLoans(): List<Loan> = listOf(loanWithMissingStrategy)
        }

        val useCaseWithMissingStrategy = GetLoansUseCase(failingRepository, calculationManager)

        useCaseWithMissingStrategy() // Should throw IllegalArgumentException
    }
}
