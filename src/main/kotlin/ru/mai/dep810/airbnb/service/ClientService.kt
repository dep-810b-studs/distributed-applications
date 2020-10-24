package ru.mai.dep810.airbnb.service

import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.dto.ClientDto
import ru.mai.dep810.airbnb.repository.IClientRepository
import ru.mai.dep810.airbnb.mapping.*
import java.util.*

interface IClientService {
    fun getAllClients() : List<ClientDto>
    fun addClient(clientDto: ClientDto): ClientDto
    fun deleteClient(uuid: UUID)
}

@Service("clientService")
class ClientService(val clientRepository: IClientRepository) : IClientService {

    override fun getAllClients(): List<ClientDto>  =
        clientRepository
                .findAll()
                .map { client -> client.toDtoModel() }


    override fun addClient(clientDto: ClientDto): ClientDto =
        clientRepository.save(clientDto.toDataModel()).toDtoModel()

    override fun deleteClient(uuid: UUID) =
            clientRepository.deleteById(uuid)
}