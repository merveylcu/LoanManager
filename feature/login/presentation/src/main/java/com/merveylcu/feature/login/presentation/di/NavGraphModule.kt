package com.merveylcu.feature.login.presentation.di

import com.merveylcu.core.navigation.NavGraphProvider
import com.merveylcu.feature.login.presentation.navigation.LoginNavGraphProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface NavGraphModule {

    @Binds
    @IntoSet
    @ActivityRetainedScoped
    fun bindsLoginNavGraphProvider(provider: LoginNavGraphProvider): NavGraphProvider
}
