package com.kldaji.loancounselor.di

import com.kldaji.domain.ClientRepository
import com.kldaji.domain.GetAllClientsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesGetAllClientsUseCase(repository: ClientRepository): GetAllClientsUseCase {
        return GetAllClientsUseCase(repository)
    }
}
