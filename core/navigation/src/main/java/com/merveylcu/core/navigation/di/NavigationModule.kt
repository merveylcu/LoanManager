package com.merveylcu.core.navigation.di

import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.ComposeNavManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

    @Binds
    @Singleton
    abstract fun bindComposeNavManager(
        impl: ComposeNavManagerImpl
    ): ComposeNavManager
}
