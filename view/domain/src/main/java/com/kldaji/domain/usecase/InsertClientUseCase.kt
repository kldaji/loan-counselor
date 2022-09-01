package com.kldaji.domain.usecase

import com.kldaji.domain.Client
import com.kldaji.domain.ClientRepository
import javax.inject.Inject

class InsertClientUseCase @Inject constructor(private val clientRepository: ClientRepository){

    suspend operator fun invoke(client: Client) {
        clientRepository.insertClient(client)
    }
}
