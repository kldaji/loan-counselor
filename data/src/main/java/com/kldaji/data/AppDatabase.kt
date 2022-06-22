package com.kldaji.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ClientEntity::class], version = 1)
@TypeConverters(
    value = [StringListTypeConverter::class]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun clientDao(): ClientDao
}
