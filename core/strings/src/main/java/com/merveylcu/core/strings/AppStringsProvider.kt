package com.merveylcu.core.strings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.merveylcu.core.strings.loan.LoanStringsImpl
import com.merveylcu.core.strings.loan.LocalLoanStrings
import com.merveylcu.core.strings.login.LocalLoginStrings
import com.merveylcu.core.strings.login.LoginStringsImpl

@Composable
fun ProvideAppStrings(context: Context, content: @Composable () -> Unit) {
    val loginStrings = remember { LoginStringsImpl(context) }
    val loanStrings = remember { LoanStringsImpl(context) }

    CompositionLocalProvider(
        LocalLoginStrings provides loginStrings,
        LocalLoanStrings provides loanStrings
    ) {
        content()
    }
}
