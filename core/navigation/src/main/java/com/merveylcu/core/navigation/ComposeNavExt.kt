package com.merveylcu.core.navigation

import android.os.Bundle
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.composable
import com.merveylcu.core.navigation.model.ComposeRouter

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val nodeId = graph.findNode(route = route)?.id
    if (nodeId != null) {
        navigate(
            resId = nodeId,
            args = args,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

fun NavGraphBuilder.registerScreen(
    composeRouter: ComposeRouter,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(route = composeRouter.route, content = content)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navGraphRoute: String,
    navController: NavController
): T {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
