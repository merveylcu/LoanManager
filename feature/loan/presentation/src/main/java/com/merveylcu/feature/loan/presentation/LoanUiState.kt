package com.merveylcu.feature.loan.presentation

import com.merveylcu.feature.loan.domain.model.LoanDetail

data class LoanUiState(
    val loans: List<LoanDetail> = emptyList()
)
