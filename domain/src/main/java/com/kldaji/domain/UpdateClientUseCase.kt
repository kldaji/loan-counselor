package com.kldaji.domain

import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(private val repository: ClientRepository) {

    suspend operator fun invoke(client: Client) {
        repository.updateClient(client)
    }
}
