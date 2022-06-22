package com.kldaji.data

import com.kldaji.domain.Client

object ClientMapper {
    fun clientToClientEntity(client: Client): ClientEntity {
        return ClientEntity(
            id = client.id,
            name = client.name,
            phone = client.phone,
            birth = client.birth,
            loan = client.loan,
            gender = client.gender,
            meeting = client.meeting,
            run = client.run,
            remark = client.remark,
            pictures = client.pictures
        )
    }

    fun clientEntityToClient(clientEntity: ClientEntity): Client {
        return Client(
            id = clientEntity.id,
            name = clientEntity.name,
            phone = clientEntity.phone,
            birth = clientEntity.birth,
            loan = clientEntity.loan,
            gender = clientEntity.gender,
            meeting = clientEntity.meeting,
            run = clientEntity.run,
            remark = clientEntity.remark,
            pictures = clientEntity.pictures
        )
    }
}
