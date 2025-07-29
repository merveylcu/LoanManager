package com.merveylcu.core.navigation.delegate

import androidx.activity.ComponentActivity
import androidx.navigation.NavHostController
import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.model.ComposeRouter

interface ComposeNavigationDelegate {

    fun attachNavigationDelegate(
        activity: ComponentActivity,
        hostScreen: ComposeRouter,
        composeNavManager: ComposeNavManager,
        navHostController: NavHostController
    )

    fun detachNavigationDelegate()
}
