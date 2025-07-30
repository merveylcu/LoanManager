package com.merveylcu.feature.loan.presentation.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.feature.loan.domain.model.LoanDetail

@Composable
internal fun LoanCard(loan: LoanDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalAppDimensions.current.paddingMedium),
        elevation = CardDefaults.cardElevation(LocalAppDimensions.current.paddingSmall)
    ) {
        LoanItem(loan)
    }
}
