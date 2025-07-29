package com.merveylcu.core.navigation

import com.merveylcu.core.common.dispatchers.Dispatchers
import com.merveylcu.core.navigation.model.NavigateFormation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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

    override fun navigate(navigateFormation: NavigateFormation) {
        launch {
            navigationChannel.send(navigateFormation)
        }
    }
}
