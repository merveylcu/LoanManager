package com.merveylcu.core.navigation.model

import android.os.Parcelable

sealed interface NavigateFormation {

    data class OpenScreen(
        val screen: ComposeRouter,
        val args: Parcelable? = null
    ) : NavigateFormation

    data class NavigateUp(val resultArgs: Parcelable? = null) : NavigateFormation

    data class PopBackStack(
        val destinationScreen: ComposeRouter? = null,
        val resultArgs: Parcelable? = null,
        val inclusive: Boolean = false
    ) : NavigateFormation
}
