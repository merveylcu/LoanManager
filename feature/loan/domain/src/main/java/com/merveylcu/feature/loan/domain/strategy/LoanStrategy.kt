package com.merveylcu.feature.loan.domain.strategy

import javax.inject.Inject

class PersonalLoanStrategy @Inject constructor() : LoanCalculationStrategy {

    override fun calculateInterestRate(amount: Double): Double = 14.0

    override fun calculateDurationMonths(amount: Double): Int = 24
}

class AutoLoanStrategy @Inject constructor() : LoanCalculationStrategy {

    override fun calculateInterestRate(amount: Double): Double =
        if (amount > 50000) 8.5 else 10.0

    override fun calculateDurationMonths(amount: Double): Int = 36
}

class MortgageLoanStrategy @Inject constructor() : LoanCalculationStrategy {

    override fun calculateInterestRate(amount: Double): Double = 6.0

    override fun calculateDurationMonths(amount: Double): Int = 120
}
