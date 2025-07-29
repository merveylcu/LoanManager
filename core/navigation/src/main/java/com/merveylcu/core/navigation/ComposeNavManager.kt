package com.merveylcu.core.navigation

import com.merveylcu.core.navigation.model.NavigateFormation
import com.merveylcu.core.navigation.model.TabSelection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ComposeNavManager {

    val navigationFlow: Flow<NavigateFormation>

    val navigationTabFlow: Flow<TabSelection>

    val scrollTabFlow: SharedFlow<TabSelection>

    fun navigate(navigateFormation: NavigateFormation)

    fun selectTab(tabSelection: TabSelection)

    fun resetReplayCache()
}
