package com.merveylcu.core.navigation.di

import com.merveylcu.core.navigation.ComposeNavManager
import com.merveylcu.core.navigation.ComposeNavManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindComposeNavManager(
        impl: ComposeNavManagerImpl
    ): ComposeNavManager
}
