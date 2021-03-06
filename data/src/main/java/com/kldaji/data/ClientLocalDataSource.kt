package com.kldaji.data

interface ClientLocalDataSource {
    suspend fun getAllClients(): List<ClientEntity>
    suspend fun insertClient(clientEntity: ClientEntity)
    suspend fun updateClient(clientEntity: ClientEntity)
    suspend fun deleteClient(clientEntity: ClientEntity)
}
