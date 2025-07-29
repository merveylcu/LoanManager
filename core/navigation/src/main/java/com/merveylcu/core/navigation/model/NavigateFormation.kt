package com.merveylcu.core.navigation.model

import android.content.Intent
import android.os.Parcelable

sealed interface NavigateFormation {

    data class OpenActivity(
        val intent: Intent,
        val pairAnimationIds: Pair<Int, Int>? = null,
        val isFinish: Boolean = false,
        val onError: ((Throwable) -> Unit)? = null
    ) : NavigateFormation

    data class OpenScreen(
        val screen: ComposeRouter,
        val args: Parcelable? = null
    ) : NavigateFormation

    data class OpenOrBackScreen(
        val destinationScreen: ComposeRouter? = null,
        val args: Parcelable? = null
    ) : NavigateFormation

    data class NavigateDialog(
        val router: ComposeRouter,
        val isOpen: Boolean,
        val args: Parcelable? = null
    ) : NavigateFormation

    data class NavigateUp(val resultArgs: Parcelable? = null) : NavigateFormation

    data object DoubleNavigateUp : NavigateFormation

    data class PopBackStack(
        val destinationScreen: ComposeRouter? = null,
        val resultArgs: Parcelable? = null,
        val inclusive: Boolean = false
    ) : NavigateFormation

    data class NavigateAction(val action: NavigationAction) : NavigateFormation
}
