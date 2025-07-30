package com.merveylcu.feature.loan.data

import com.merveylcu.feature.loan.domain.model.Loan
import com.merveylcu.feature.loan.domain.model.LoanType

internal object DummyLoanDataSource {

    val loans = listOf(
        Loan("1", LoanType.PERSONAL, 10000.0),
        Loan("2", LoanType.AUTO, 25000.0),
        Loan("3", LoanType.MORTGAGE, 150000.0)
    )
}
