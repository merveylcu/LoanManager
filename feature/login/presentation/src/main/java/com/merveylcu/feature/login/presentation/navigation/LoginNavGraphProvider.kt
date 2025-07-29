package com.merveylcu.feature.login.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.core.navigation.registerScreen
import javax.inject.Inject

internal class LoginNavGraphProvider @Inject constructor() : NavGraphProvider {

    override fun build(navGraphBuilder: NavGraphBuilder, navController: NavController) {
        navGraphBuilder.apply {
            registerScreen(composeRouter = LoginRouter.Login) {
                Greeting("Merve")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}