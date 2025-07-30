package com.merveylcu.feature.loan.domain.model

data class LoanDetail(
    val id: String,
    val type: LoanType,
    val amount: Double,
    val interestRate: Double,
    val durationMonths: Int
)
