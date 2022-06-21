package com.kldaji.data

interface ClientLocalDataSource {
    suspend fun getAllClients(): List<ClientEntity>
    suspend fun insertClient(clientEntity: ClientEntity)
}
