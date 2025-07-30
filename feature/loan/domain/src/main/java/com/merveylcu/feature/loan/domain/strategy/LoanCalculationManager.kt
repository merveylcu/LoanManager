package com.merveylcu.feature.loan.domain.strategy

import com.merveylcu.feature.loan.domain.model.LoanType
import javax.inject.Inject

class LoanCalculationManager @Inject constructor(
    private val strategyMap: Map<LoanType, LoanCalculationStrategy>
) {

    fun getStrategy(type: LoanType): LoanCalculationStrategy {
        return strategyMap[type]
            ?: throw IllegalArgumentException("No strategy for type: $type")
    }
}
