package com.merveylcu.core.navigation.model

import android.os.Parcelable

data class TabSelection(val screenRouter: ComposeRouter, val args: Parcelable? = null)
