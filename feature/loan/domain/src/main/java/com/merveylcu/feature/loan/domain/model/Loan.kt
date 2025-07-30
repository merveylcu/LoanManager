package com.merveylcu.feature.loan.domain.model

data class Loan(
    val id: String,
    val type: LoanType,
    val amount: Double
)
