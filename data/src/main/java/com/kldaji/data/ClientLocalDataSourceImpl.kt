package com.kldaji.data

import javax.inject.Inject

class ClientLocalDataSourceImpl @Inject constructor(private val clientDao: ClientDao): ClientLocalDataSource {
    override suspend fun getAllClients(): List<ClientEntity> {
        return clientDao.getAllClients()
    }

    override suspend fun insertClient(clientEntity: ClientEntity) {
        clientDao.insertClient(clientEntity)
    }
}
