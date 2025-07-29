package com.merveylcu.core.navigation

import com.merveylcu.core.common.dispatchers.Dispatchers
import com.merveylcu.core.navigation.model.NavigateFormation
import com.merveylcu.core.navigation.model.TabSelection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ComposeNavManagerImpl @Inject constructor(
    @Dispatchers.Main dispatcher: CoroutineDispatcher
) : CoroutineScope, ComposeNavManager {

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    private val navigationChannel = Channel<NavigateFormation>()
    override val navigationFlow: Flow<NavigateFormation>
        get() = navigationChannel.receiveAsFlow()

    private val navigationTabChannel = Channel<TabSelection>()
    override val navigationTabFlow: Flow<TabSelection>
        get() = navigationTabChannel.receiveAsFlow()

    private val scrollTabChannel = MutableSharedFlow<TabSelection>(replay = 2)
    override val scrollTabFlow: SharedFlow<TabSelection>
        get() = scrollTabChannel.asSharedFlow()

    override fun navigate(navigateFormation: NavigateFormation) {
        launch {
            navigationChannel.send(navigateFormation)
        }
    }

    override fun selectTab(tabSelection: TabSelection) {
        launch {
            navigationTabChannel.send(tabSelection)
            scrollTabChannel.emit(tabSelection)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun resetReplayCache() {
        if (scrollTabChannel.replayCache.isNotEmpty()) {
            scrollTabChannel.resetReplayCache()
        }
    }
}
