package com.kldaji.domain

interface ClientRepository {
    fun getAllClients(): List<Client>
    fun insertClient(client: Client)
}
