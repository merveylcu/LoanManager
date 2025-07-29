package com.merveylcu.core.navigation.delegate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.merveylcu.core.common.extensions.openBrowserDirectly
import com.merveylcu.core.common.extensions.openPhoneCall
import com.merveylcu.core.navigation.BACK_STACK_ENTRY_ARGS_KEY
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.SCREEN_ARGS_KEY
import com.merveylcu.core.navigation.model.ComposeRouter
import com.merveylcu.core.navigation.model.ComposeTabScreenRouter
import com.merveylcu.core.navigation.model.NavigateFormation
import com.merveylcu.core.navigation.model.NavigationAction
import com.merveylcu.core.navigation.model.TabSelection
import com.merveylcu.core.navigation.navigate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ComposeNavigationDelegateImpl : ComposeNavigationDelegate {

    private var navigationJob: Job? = null
    private var hostScreen: ComposeRouter = ComposeTabScreenRouter(
        route = "home"
    )

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
                        is NavigateFormation.OpenActivity -> openActivity(
                            activity = activity,
                            intent = formation.intent,
                            pairAnimationIds = formation.pairAnimationIds,
                            isFinish = formation.isFinish,
                            onError = formation.onError
                        )

                        is NavigateFormation.OpenScreen -> navigationOpenScreen(
                            navHostController = navHostController,
                            composeNavManager = composeNavManager,
                            formation = formation,
                            navOptions = navOptions {
                                launchSingleTop = true
                                restoreState = true
                            }
                        )

                        is NavigateFormation.NavigateDialog -> navigateDialog(
                            navHostController = navHostController,
                            formation = formation
                        )

                        is NavigateFormation.OpenOrBackScreen -> navigationOpenOrBackScreen(
                            navHostController = navHostController,
                            composeNavManager = composeNavManager,
                            formation = formation
                        )

                        is NavigateFormation.NavigateUp -> {
                            navigateUp(
                                navHostController = navHostController,
                                formation = formation
                            )
                        }

                        is NavigateFormation.DoubleNavigateUp -> doubleNavigateUp(
                            navHostController = navHostController
                        )

                        is NavigateFormation.PopBackStack -> popBackStack(
                            navHostController = navHostController,
                            composeNavManager = composeNavManager,
                            formation = formation
                        )

                        is NavigateFormation.NavigateAction -> navigationAction(
                            activity = activity,
                            action = formation.action
                        )

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun openActivity(
        activity: ComponentActivity,
        intent: Intent,
        pairAnimationIds: Pair<Int, Int>? = null,
        isFinish: Boolean,
        onError: ((Throwable) -> Unit)? = null
    ) {
        runCatching {
            activity.startActivity(intent)
            pairAnimationIds?.let {
                activity.overridePendingTransition(pairAnimationIds.first, pairAnimationIds.second)
            }
            if (isFinish) {
                activity.finish()
            }
        }.onFailure {
            onError?.invoke(it)
        }
    }

    private fun navigationOpenScreen(
        navHostController: NavHostController,
        composeNavManager: ComposeNavManager,
        formation: NavigateFormation.OpenScreen,
        navOptions: NavOptions? = null
    ) {
        val bundle = Bundle()
        formation.args?.let { args ->
            bundle.putParcelable(SCREEN_ARGS_KEY, args)
        }

        if (formation.screen is ComposeTabScreenRouter) {
            internPopBackStack(navHostController = navHostController)
            composeNavManager.selectTab(
                tabSelection = TabSelection(
                    screenRouter = formation.screen,
                    args = formation.args
                )
            )
        } else {
            navHostController.navigate(
                route = formation.screen.route,
                args = bundle,
                navOptions = navOptions
            )
        }
    }

    @SuppressLint("RestrictedApi")
    private fun navigationOpenOrBackScreen(
        navHostController: NavHostController,
        composeNavManager: ComposeNavManager,
        formation: NavigateFormation.OpenOrBackScreen
    ) {
        formation.destinationScreen?.let { composeRouterScreen ->
            navHostController.currentBackStack.value.find {
                it.destination.route == composeRouterScreen.route
            }?.let {
                internPopBackStack(
                    navHostController = navHostController,
                    route = composeRouterScreen.route,
                    resultArgs = formation.args
                )
                return
            }

            navigationOpenScreen(
                navHostController = navHostController,
                composeNavManager = composeNavManager,
                formation = NavigateFormation.OpenScreen(
                    screen = composeRouterScreen,
                    args = formation.args
                ),
                navOptions = navOptions {
                    popUpTo(route = hostScreen.route)
                    launchSingleTop = true
                    restoreState = true
                }
            )
        } ?: run {
            internPopBackStack(navHostController = navHostController)
        }
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
        composeNavManager: ComposeNavManager,
        formation: NavigateFormation.PopBackStack
    ) {
        if (formation.destinationScreen is ComposeTabScreenRouter) {
            internPopBackStack(
                navHostController = navHostController,
                resultArgs = formation.resultArgs
            )
            formation.destinationScreen?.let {
                composeNavManager.selectTab(tabSelection = TabSelection(screenRouter = it))
            }
        } else {
            internPopBackStack(
                navHostController = navHostController,
                route = formation.destinationScreen?.route,
                resultArgs = formation.resultArgs,
                inclusive = formation.inclusive
            )
        }
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

    @SuppressLint("RestrictedApi")
    private fun navigateDialog(
        navHostController: NavHostController,
        formation: NavigateFormation.NavigateDialog
    ) {
        if (formation.isOpen) {

            val bundle = Bundle()
            formation.args?.let { args ->
                bundle.putParcelable(SCREEN_ARGS_KEY, args)
            }

            navHostController.navigate(
                route = formation.router.route,
                args = bundle
            )
        } else {
            navHostController.currentBackStack.value.find {
                it.destination.route == formation.router.route
            }?.let {
                navHostController.popBackStack()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun doubleNavigateUp(navHostController: NavHostController) {
        navHostController.currentBackStack.value.let { stackList ->
            val destinationScreenRoute = stackList.getOrNull(stackList.size - 3)
            destinationScreenRoute?.let { route ->
                internPopBackStack(
                    navHostController = navHostController,
                    route = route.destination.route
                )
                return
            }
        }

        internPopBackStack(navHostController = navHostController)
    }

    private fun navigationAction(activity: Activity, action: NavigationAction) {
        when (action) {
            is NavigationAction.CallPhone -> activity.openPhoneCall(action.number)

            is NavigationAction.OpenBrowser -> activity.openBrowserDirectly(action.url)
        }
    }
}
