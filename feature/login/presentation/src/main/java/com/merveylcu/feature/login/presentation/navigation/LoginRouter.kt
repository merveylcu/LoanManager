package com.merveylcu.feature.login.presentation.navigation

import com.merveylcu.core.navigation.model.ComposeRouter
import com.merveylcu.core.navigation.model.ComposeScreenRouter

object LoginRouter {

    val Login: ComposeRouter
        get() = ComposeScreenRouter(route = "login")
}
