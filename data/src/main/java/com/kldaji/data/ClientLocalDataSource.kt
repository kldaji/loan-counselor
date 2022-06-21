package com.kldaji.data

interface ClientLocalDataSource {
    fun getAllClients(): List<ClientEntity>
    fun insertClient(clientEntity: ClientEntity)
}
