package com.merveylcu.feature.loan.domain.strategy

interface LoanCalculationStrategy {

    fun calculateInterestRate(amount: Double): Double

    fun calculateDurationMonths(amount: Double): Int
}