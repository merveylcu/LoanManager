package com.merveylcu.loanmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.core.navigation.delegate.ComposeNavigationDelegate
import com.merveylcu.core.navigation.delegate.ComposeNavigationDelegateImpl
import com.merveylcu.core.navigation.registerScreen
import com.merveylcu.core.theme.AppTheme
import com.merveylcu.feature.login.presentation.navigation.LoginRouter
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    ComposeNavigationDelegate by ComposeNavigationDelegateImpl() {

    @Inject
    lateinit var composeNavManager: Lazy<ComposeNavManager>

    @Inject
    @JvmSuppressWildcards
    lateinit var navGraphProvider: Lazy<Set<NavGraphProvider>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LoginRouter.Login.route
                ) {
                    registerScreen(composeRouter = LoginRouter.Login) { homeBackStackEntry ->

                    }

                    for (provider in navGraphProvider.get()) {
                        provider.build(this, navController)
                    }
                }

                attachNavigationDelegate(
                    activity = this,
                    hostScreen = LoginRouter.Login,
                    composeNavManager = composeNavManager.get(),
                    navHostController = navController
                )
            }
        }
    }

    override fun onDestroy() {
        detachNavigationDelegate()
        super.onDestroy()
    }
}
