package com.merveylcu.feature.loan.presentation

import com.merveylcu.feature.loan.domain.model.LoanDetail

internal data class LoanUiState(
    val loans: List<LoanDetail> = emptyList()
)
