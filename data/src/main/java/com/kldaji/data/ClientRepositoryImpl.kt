package com.kldaji.data

import com.kldaji.domain.Client
import com.kldaji.domain.ClientRepository
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(private val clientLocalDataSource: ClientLocalDataSource) :
    ClientRepository {
    override suspend fun getAllClients(): List<Client> {
        return clientLocalDataSource.getAllClients().map { ClientMapper.clientEntityToClient(it) }
    }

    override suspend fun insertClient(client: Client) {
        clientLocalDataSource.insertClient(ClientMapper.clientToClientEntity(client))
    }
}
