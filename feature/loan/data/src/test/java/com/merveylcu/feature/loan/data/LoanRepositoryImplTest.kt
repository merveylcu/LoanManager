package com.merveylcu.feature.loan.data

import com.merveylcu.feature.loan.domain.LoanRepository
import com.merveylcu.feature.loan.domain.model.Loan
import com.merveylcu.feature.loan.domain.model.LoanType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoanRepositoryImplTest {

    private val repository: LoanRepository = LoanRepositoryImpl()

    @Test
    fun `getLoans returns expected dummy data`() = runTest {
        // Given
        val expected = listOf(
            Loan("1", LoanType.PERSONAL, 10000.0),
            Loan("2", LoanType.AUTO, 25000.0),
            Loan("3", LoanType.MORTGAGE, 150000.0)
        )

        // When
        val result = repository.getLoans()

        // Then
        assertEquals(expected, result)
    }
}
