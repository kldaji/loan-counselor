package com.kldaji.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ClientEntity::class], version = 1)
abstract class ClientDatabase: RoomDatabase() {
    abstract fun clientDao(): ClientDao
}