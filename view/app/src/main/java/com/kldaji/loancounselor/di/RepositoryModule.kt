package com.kldaji.loancounselor.di

import com.kldaji.data.ClientRepositoryImpl
import com.kldaji.domain.ClientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsClientRepository(clientRepositoryImpl: ClientRepositoryImpl): ClientRepository
}
