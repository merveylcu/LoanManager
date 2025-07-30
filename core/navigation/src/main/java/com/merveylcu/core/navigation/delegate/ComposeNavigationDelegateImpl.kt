package com.merveylcu.core.navigation.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.merveylcu.core.navigation.BACK_STACK_ENTRY_ARGS_KEY
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.SCREEN_ARGS_KEY
import com.merveylcu.core.navigation.model.ComposeRouter
import com.merveylcu.core.navigation.model.ComposeScreenRouter
import com.merveylcu.core.navigation.model.NavigateFormation
import com.merveylcu.core.navigation.navigate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ComposeNavigationDelegateImpl : ComposeNavigationDelegate {

    private var navigationJob: Job? = null
    private var hostScreen: ComposeRouter = ComposeScreenRouter(route = "loans")

    override fun attachNavigationDelegate(
        activity: ComponentActivity,
        hostScreen: ComposeRouter,
        composeNavManager: ComposeNavManager,
        navHostController: NavHostController
    ) {
        this.hostScreen = hostScreen
        collectNavigation(
            activity = activity,
            composeNavManager = composeNavManager,
            navHostController = navHostController
        )
    }

    override fun detachNavigationDelegate() {
        runCatching {
            navigationJob?.cancel()
            navigationJob = null
        }
    }

    private fun collectNavigation(
        activity: ComponentActivity,
        composeNavManager: ComposeNavManager,
        navHostController: NavHostController
    ) {
        navigationJob?.cancel()
        navigationJob = activity.lifecycleScope.launch {
            activity.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                composeNavManager.navigationFlow.collectLatest { formation ->
                    when (formation) {
                        is NavigateFormation.OpenScreen -> navigationOpenScreen(
                            navHostController = navHostController,
                            formation = formation,
                            navOptions = navOptions {
                                launchSingleTop = true
                                restoreState = true
                            }
                        )

                        is NavigateFormation.NavigateUp -> {
                            navigateUp(
                                navHostController = navHostController,
                                formation = formation
                            )
                        }

                        is NavigateFormation.PopBackStack -> popBackStack(
                            navHostController = navHostController,
                            formation = formation
                        )
                    }
                }
            }
        }
    }

    private fun navigationOpenScreen(
        navHostController: NavHostController,
        formation: NavigateFormation.OpenScreen,
        navOptions: NavOptions? = null
    ) {
        val bundle = Bundle()
        formation.args?.let { args ->
            bundle.putParcelable(SCREEN_ARGS_KEY, args)
        }

        navHostController.navigate(
            route = formation.screen.route,
            args = bundle,
            navOptions = navOptions
        )
    }

    private fun navigateUp(
        navHostController: NavHostController,
        formation: NavigateFormation.NavigateUp
    ) {
        formation.resultArgs?.let {
            navHostController.previousBackStackEntry?.savedStateHandle?.set(
                BACK_STACK_ENTRY_ARGS_KEY,
                it
            )
        }
        navHostController.navigateUp()
    }

    private fun popBackStack(
        navHostController: NavHostController,
        formation: NavigateFormation.PopBackStack
    ) {
        internPopBackStack(
            navHostController = navHostController,
            route = formation.destinationScreen?.route,
            resultArgs = formation.resultArgs,
            inclusive = formation.inclusive
        )
    }

    @SuppressLint("RestrictedApi")
    private fun internPopBackStack(
        navHostController: NavHostController,
        route: String? = null,
        resultArgs: Parcelable? = null,
        inclusive: Boolean = false
    ) {
        if (!route.isNullOrEmpty()) {
            val backStackValue = navHostController.currentBackStack.value.find {
                it.destination.route == route
            }
            if (backStackValue != null) {
                if (resultArgs != null) {
                    (
                            navHostController.currentBackStack.value.find { it.destination.route == route }
                                ?: navHostController.previousBackStackEntry
                            )?.savedStateHandle?.set(
                            BACK_STACK_ENTRY_ARGS_KEY,
                            resultArgs
                        )
                }
                navHostController.popBackStack(
                    route = route,
                    inclusive = inclusive
                )
                return
            }
        }

        resultArgs?.let {
            navHostController.previousBackStackEntry?.savedStateHandle?.set(
                BACK_STACK_ENTRY_ARGS_KEY,
                it
            )
        }
        navHostController.popBackStack(
            route = hostScreen.route,
            inclusive = inclusive
        )
    }
}
