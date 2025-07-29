package com.merveylcu.feature.login.presentation.navigation

import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.model.NavigateFormation
import javax.inject.Inject

internal class LoginNavigation @Inject constructor(
    private val composeNavManager: ComposeNavManager
) {

    fun navigateToLogin() = composeNavManager.navigate(
        navigateFormation = NavigateFormation.OpenScreen(
            screen = LoginRouter.Login
        )
    )
}
