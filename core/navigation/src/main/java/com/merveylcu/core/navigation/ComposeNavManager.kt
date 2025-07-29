package com.merveylcu.core.navigation

import com.merveylcu.core.navigation.model.NavigateFormation
import kotlinx.coroutines.flow.Flow

interface ComposeNavManager {

    val navigationFlow: Flow<NavigateFormation>

    fun navigate(navigateFormation: NavigateFormation)
}
