package com.kldaji.domain

import javax.inject.Inject

class InsertClientUseCase @Inject constructor(private val clientRepository: ClientRepository){

    suspend operator fun invoke(client: Client) {
        clientRepository.insertClient(client)
    }
}
