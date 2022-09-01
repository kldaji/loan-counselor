package com.kldaji.loancounselor.di

import com.kldaji.data.ClientLocalDataSource
import com.kldaji.data.ClientLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindsClientLocalDataSource(clientLocalDataSourceImpl: ClientLocalDataSourceImpl): ClientLocalDataSource
}
