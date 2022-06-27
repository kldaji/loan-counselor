package com.kldaji.domain

interface ClientRepository {
    suspend fun getAllClients(): List<Client>
    suspend fun insertClient(client: Client)
    suspend fun updateClient(client: Client)
    suspend fun deleteClient(client: Client)
}
