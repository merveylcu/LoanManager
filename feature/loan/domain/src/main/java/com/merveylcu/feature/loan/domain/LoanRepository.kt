package com.merveylcu.feature.loan.domain

import com.merveylcu.feature.loan.domain.model.Loan

interface LoanRepository {

    suspend fun getLoans(): List<Loan>
}
