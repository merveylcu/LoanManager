package com.merveylcu.feature.loan.presentation.di

import com.merveylcu.core.common.navigation.LoanNavigation
import com.merveylcu.feature.loan.presentation.navigation.LoanNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface LoanNavigationModule {

    @Binds
    @ActivityRetainedScoped
    fun bindsLoanNavigation(provider: LoanNavigationImpl): LoanNavigation
}
