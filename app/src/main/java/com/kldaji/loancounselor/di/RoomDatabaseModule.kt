package com.kldaji.loancounselor.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kldaji.data.ClientDao
import com.kldaji.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "client.db").build()
    }

    @Provides
    @Singleton
    fun providesClientDao(appDatabase: AppDatabase): ClientDao {
        return appDatabase.clientDao()
    }
}
