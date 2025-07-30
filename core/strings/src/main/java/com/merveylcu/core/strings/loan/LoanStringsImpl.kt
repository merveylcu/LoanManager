package com.merveylcu.core.strings.loan

import android.content.Context
import com.merveylcu.core.strings.R

class LoanStringsImpl(private val context: Context) : LoanStrings {

    override val loansPageTitle: String
        get() = context.getString(R.string.loans_page_title)

    override val loanType: String
        get() = context.getString(R.string.loan_type)

    override val loanAmount: String
        get() = context.getString(R.string.loan_amount)

    override val loanInterestRate: String
        get() = context.getString(R.string.loan_interest_rate)

    override val loanDuration: String
        get() = context.getString(R.string.loan_duration)

    override val logout: String
        get() = context.getString(R.string.logout)
}
