package com.merveylcu.loanmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.merveylcu.core.common.sharedpref.LoanPreferences
import com.merveylcu.core.common.sharedpref.LoanPreferencesKey
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.core.navigation.delegate.ComposeNavigationDelegate
import com.merveylcu.core.navigation.delegate.ComposeNavigationDelegateImpl
import com.merveylcu.core.strings.ProvideAppStrings
import com.merveylcu.core.theme.AppTheme
import com.merveylcu.feature.loan.presentation.navigation.LoanRouter
import com.merveylcu.feature.login.presentation.navigation.LoginRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(),
    ComposeNavigationDelegate by ComposeNavigationDelegateImpl() {

    @Inject
    lateinit var loanPreferences: LoanPreferences

    @Inject
    lateinit var composeNavManager: ComposeNavManager

    @Inject
    lateinit var navGraphProvider: Set<NavGraphProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLoggedIn = loanPreferences.getString(LoanPreferencesKey.Email) != null &&
                loanPreferences.getString(LoanPreferencesKey.Password) != null

        enableEdgeToEdge()
        setContent {
            AppTheme {
                ProvideAppStrings(context = this) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) LoanRouter.Loans.route else LoginRouter.Login.route
                    ) {
                        for (provider in navGraphProvider) {
                            provider.build(this, navController)
                        }
                    }

                    LaunchedEffect(Unit) {
                        attachNavigationDelegate(
                            activity = this@MainActivity,
                            hostScreen = LoginRouter.Login,
                            composeNavManager = composeNavManager,
                            navHostController = navController
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        detachNavigationDelegate()
        super.onDestroy()
    }
}
