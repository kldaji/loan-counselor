package com.kldaji.domain

import javax.inject.Inject

class GetAllClientsUseCase @Inject constructor(private val clientRepository: ClientRepository) {

    suspend operator fun invoke(): List<Client> {
        return clientRepository.getAllClients()
    }
}
