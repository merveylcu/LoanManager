package com.merveylcu.feature.loan.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.merveylcu.component.text.LoanText
import com.merveylcu.core.theme.LocalAppDimensions
import com.merveylcu.feature.loan.domain.model.LoanDetail

@Composable
internal fun LoanItem(loan: LoanDetail) {
    Column(modifier = Modifier.padding(LocalAppDimensions.current.paddingMedium)) {
        LoanText(text = "Loan Type: ${loan.type}")
        LoanText(text = "Amount: ${loan.amount} ₺")
        LoanText(text = "Interest Rate: ${loan.interestRate} %")
        LoanText(text = "Duration: ${loan.durationMonths} months")
    }
}
