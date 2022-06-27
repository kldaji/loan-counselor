package com.kldaji.domain.usecase

import com.kldaji.domain.Client
import com.kldaji.domain.ClientRepository
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(private val repository: ClientRepository) {
    suspend operator fun invoke(client: Client) {
        repository.deleteClient(client)
    }
}
