package com.kldaji.data

import com.kldaji.domain.Client
import com.kldaji.domain.ClientRepository

class ClientRepositoryImpl(private val clientLocalDataSource: ClientLocalDataSource) :
    ClientRepository {
    override fun getAllClients(): List<Client> {
        return clientLocalDataSource.getAllClients().map { ClientMapper.clientEntityToClient(it) }
    }

    override fun insertClient(client: Client) {
        clientLocalDataSource.insertClient(ClientMapper.clientToClientEntity(client))
    }
}
