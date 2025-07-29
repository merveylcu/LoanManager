package com.merveylcu.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavGraphProvider {

    fun build(navGraphBuilder: NavGraphBuilder, navController: NavController)
}
