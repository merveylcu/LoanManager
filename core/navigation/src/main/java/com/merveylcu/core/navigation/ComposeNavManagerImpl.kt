package com.merveylcu.core.navigation

import com.merveylcu.core.common.dispatchers.Dispatchers
import com.merveylcu.core.navigation.model.NavigateFormation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ComposeNavManagerImpl @Inject constructor(
    @Dispatchers.Main dispatcher: CoroutineDispatcher
) : CoroutineScope, ComposeNavManager {

    override val coroutineContext: CoroutineContext = dispatcher + SupervisorJob()

    private val _navigationFlow = MutableSharedFlow<NavigateFormation>(
        extraBufferCapacity = 1, replay = 0
    )
    override val navigationFlow: SharedFlow<NavigateFormation> = _navigationFlow.asSharedFlow()

    override fun navigate(navigateFormation: NavigateFormation) {
        _navigationFlow.tryEmit(navigateFormation)
    }
}
