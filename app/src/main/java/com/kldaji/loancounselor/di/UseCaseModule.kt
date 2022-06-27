package com.kldaji.loancounselor.di

import com.kldaji.domain.ClientRepository
import com.kldaji.domain.usecase.GetAllClientsUseCase
import com.kldaji.domain.usecase.InsertClientUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

    @Provides
    @Singleton
    fun providesInsertClientUseCase(repository: ClientRepository): InsertClientUseCase {
        return InsertClientUseCase(repository)
    }
}
