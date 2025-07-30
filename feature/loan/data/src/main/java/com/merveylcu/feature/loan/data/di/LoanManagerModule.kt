package com.merveylcu.feature.loan.data.di

import com.merveylcu.feature.loan.data.LoanRepositoryImpl
import com.merveylcu.feature.loan.domain.GetLoansUseCase
import com.merveylcu.feature.loan.domain.LoanRepository
import com.merveylcu.feature.loan.domain.model.LoanType
import com.merveylcu.feature.loan.domain.strategy.AutoLoanStrategy
import com.merveylcu.feature.loan.domain.strategy.LoanCalculationManager
import com.merveylcu.feature.loan.domain.strategy.MortgageLoanStrategy
import com.merveylcu.feature.loan.domain.strategy.PersonalLoanStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LoanModule {

    @Provides
    fun provideLoanRepository(): LoanRepository = LoanRepositoryImpl()

    @Provides
    fun provideLoanCalculationManager(): LoanCalculationManager {
        return LoanCalculationManager(
            mapOf(
                LoanType.PERSONAL to PersonalLoanStrategy(),
                LoanType.AUTO to AutoLoanStrategy(),
                LoanType.MORTGAGE to MortgageLoanStrategy()
            )
        )
    }

    @Provides
    fun provideGetCalculatedLoanListUseCase(
        repository: LoanRepository,
        manager: LoanCalculationManager
    ): GetLoansUseCase {
        return GetLoansUseCase(repository, manager)
    }
}
