package com.kldaji.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClientDao {
    @Query("SELECT * FROM client ORDER BY name ASC")
    suspend fun getAllClients(): List<ClientEntity>

    @Insert
    suspend fun insertClient(clientEntity: ClientEntity)

    @Update
    suspend fun updateClient(clientEntity: ClientEntity)
}
