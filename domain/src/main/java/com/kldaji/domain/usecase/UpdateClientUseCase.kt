package com.kldaji.domain.usecase

import com.kldaji.domain.Client
import com.kldaji.domain.ClientRepository
import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(private val repository: ClientRepository) {

    suspend operator fun invoke(client: Client) {
        repository.updateClient(client)
    }
}
