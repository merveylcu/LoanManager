package com.merveylcu.core.navigation

import com.merveylcu.core.navigation.model.NavigateFormation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ComposeNavManager {

    val navigationFlow: SharedFlow<NavigateFormation>

    fun navigate(navigateFormation: NavigateFormation)
}
