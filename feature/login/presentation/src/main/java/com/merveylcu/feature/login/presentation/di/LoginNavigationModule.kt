package com.merveylcu.feature.login.presentation.di

import com.merveylcu.core.common.navigation.LoginNavigation
import com.merveylcu.feature.login.presentation.navigation.LoginNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface LoginNavigationModule {

    @Binds
    @ActivityRetainedScoped
    fun bindsLoginNavigation(provider: LoginNavigationImpl): LoginNavigation
}
