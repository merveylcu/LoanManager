package com.merveylcu.feature.loan.presentation.navigation

import com.merveylcu.core.common.navigation.LoanNavigation
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.model.NavigateFormation
import javax.inject.Inject

internal class LoanNavigationImpl @Inject constructor(
    private val composeNavManager: ComposeNavManager
) : LoanNavigation {

    override fun navigateToLoans() = composeNavManager.navigate(
        navigateFormation = NavigateFormation.OpenScreen(
            screen = LoanRouter.Loans
        )
    )
}
