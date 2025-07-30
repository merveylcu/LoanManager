package com.merveylcu.feature.loan.domain

import com.merveylcu.feature.loan.domain.model.LoanDetail
import com.merveylcu.feature.loan.domain.strategy.LoanCalculationManager
import javax.inject.Inject

class GetLoansUseCase @Inject constructor(
    private val repository: LoanRepository,
    private val calculationManager: LoanCalculationManager
) {

    suspend operator fun invoke(): List<LoanDetail> {
        val rawLoans = repository.getLoans()
        return rawLoans.map { loan ->
            val strategy = calculationManager.getStrategy(loan.type)
            LoanDetail(
                id = loan.id,
                type = loan.type,
                amount = loan.amount,
                interestRate = strategy.calculateInterestRate(loan.amount),
                durationMonths = strategy.calculateDurationMonths(loan.amount)
            )
        }
    }
}
