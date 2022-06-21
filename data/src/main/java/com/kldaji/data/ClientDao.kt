package com.kldaji.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClientDao {
    @Query("SELECT * FROM client ORDER BY name ASC")
    fun getAllClients(): List<ClientEntity>

    @Insert
    fun insertClient(clientEntity: ClientEntity)
}
