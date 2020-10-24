package ru.mai.dep810.airbnb.mapping

import ru.mai.dep810.airbnb.data.Client
import ru.mai.dep810.airbnb.dto.ClientDto

fun ClientDto.toDataModel() : Client = Client(
        id = this.id,
        name = this.name
)

fun Client.toDtoModel() : ClientDto = ClientDto(
        id = this.id,
        name = this.name
)
