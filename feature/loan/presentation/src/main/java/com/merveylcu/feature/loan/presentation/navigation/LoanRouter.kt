package com.merveylcu.feature.loan.presentation.navigation

import com.merveylcu.core.navigation.model.ComposeRouter
import com.merveylcu.core.navigation.model.ComposeScreenRouter

object LoanRouter {

    val Loans: ComposeRouter
        get() = ComposeScreenRouter(route = "loans")
}
