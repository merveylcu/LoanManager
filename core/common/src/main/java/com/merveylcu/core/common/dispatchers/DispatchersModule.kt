package com.merveylcu.core.common.dispatchers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    @Provides
    @com.merveylcu.core.common.dispatchers.Dispatchers.IO
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @com.merveylcu.core.common.dispatchers.Dispatchers.Main
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @com.merveylcu.core.common.dispatchers.Dispatchers.Default
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @com.merveylcu.core.common.dispatchers.Dispatchers.Unconfined
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
