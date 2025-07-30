package com.merveylcu.feature.login.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.core.navigation.registerScreen
import com.merveylcu.feature.login.presentation.LoginRootScreen
import javax.inject.Inject

internal class LoginNavGraphProvider @Inject constructor() : NavGraphProvider {

    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            registerScreen(composeRouter = LoginRouter.Login) {
                LoginRootScreen()
            }
        }
    }
}
