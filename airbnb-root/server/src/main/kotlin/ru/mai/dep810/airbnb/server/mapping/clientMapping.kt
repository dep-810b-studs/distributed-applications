package ru.mai.dep810.airbnb.server.mapping

import ru.mai.dep810.airbnb.server.data.Client
import ru.mai.dep810.airbnb.server.dto.ClientDto
import java.util.*

fun ClientDto.toDataModel() : Client = Client(
        id = UUID.randomUUID().toString(),
        name = this.name,
        creationDate = this.creationDate
)

fun Client.toDtoModel() : ClientDto = ClientDto(
        name = this.name,
        creationDate = this.creationDate
)

