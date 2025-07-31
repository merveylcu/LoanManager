package com.merveylcu.core.common.event

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventManagerModule {

    @Provides
    @Singleton
    fun provideEventManager(events: List<EventTracker>) = EventManager(events)

    @Provides
    @IntoSet
    fun provideAnalyticEvent() = AnalyticEvent()

    @Provides
    @IntoSet
    fun provideAdjustEvent() = AdjustEvent()

}