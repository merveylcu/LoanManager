package com.merveylcu.core.navigation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface NavigationAction : Parcelable {

    @Parcelize
    data class CallPhone(val number: String) : NavigationAction

    @Parcelize
    data class OpenBrowser(val url: String) : NavigationAction
}
