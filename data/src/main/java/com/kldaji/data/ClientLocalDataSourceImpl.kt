package com.kldaji.data

class ClientLocalDataSourceImpl(private val clientDao: ClientDao): ClientLocalDataSource {
    override fun getAllClients(): List<ClientEntity> {
        return clientDao.getAllClients()
    }

    override fun insertClient(clientEntity: ClientEntity) {
        clientDao.insertClient(clientEntity)
    }
}
