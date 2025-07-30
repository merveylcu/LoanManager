package com.merveylcu.feature.loan.data

import com.merveylcu.feature.loan.domain.model.Loan
import com.merveylcu.feature.loan.domain.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor() : LoanRepository {

    override suspend fun getLoans(): List<Loan> {
        return DummyLoanDataSource.loans
    }
}
