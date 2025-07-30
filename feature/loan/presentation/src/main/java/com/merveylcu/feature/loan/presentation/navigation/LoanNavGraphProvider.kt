package com.merveylcu.feature.loan.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.core.navigation.registerScreen
import com.merveylcu.feature.loan.presentation.LoanRootScreen
import javax.inject.Inject

internal class LoanNavGraphProvider @Inject constructor() : NavGraphProvider {

    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            registerScreen(composeRouter = LoanRouter.Loans) {
                LoanRootScreen()
            }
        }
    }
}
