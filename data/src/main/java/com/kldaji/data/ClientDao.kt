package com.kldaji.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClientDao {
    @Query("SELECT * FROM client ORDER BY name ASC")
    suspend fun getAllClients(): List<ClientEntity> = listOf()

    @Insert
    suspend fun insertClient(clientEntity: ClientEntity)
}
