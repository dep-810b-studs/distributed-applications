package ru.mai.dep810.airbnb.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.configuration.DumpConfiguration
import ru.mai.dep810.airbnb.server.dto.ClientDto
import ru.mai.dep810.airbnb.server.repository.MongoClientRepository
import ru.mai.dep810.airbnb.server.mapping.toDataModel
import ru.mai.dep810.airbnb.server.mapping.toDtoModel
import ru.mai.dep810.airbnb.server.utils.ClientsDataImporter
import java.util.*
import javax.annotation.PostConstruct

interface IClientService {
    fun getAllClients() : List<ClientDto>
    fun addClient(clientDto: ClientDto): ClientDto
    fun addClients(clients: List<ClientDto>)
    fun deleteClient(uuid: UUID)
}

@Service("clientService")
class ClientService : IClientService {

    @Autowired
    private lateinit var clientRepository: MongoClientRepository
    @Autowired
    private lateinit var dumpConfiguration: DumpConfiguration

    override fun getAllClients(): List<ClientDto>  =
        clientRepository
                .findAll()
                .map { client -> client.toDtoModel() }

    override fun addClient(clientDto: ClientDto): ClientDto {
        val clientData = clientDto.toDataModel()
        val insertedClientInstance = clientRepository.save(clientData)
        return insertedClientInstance.toDtoModel()
    }

    override fun addClients(clients: List<ClientDto>) =
        clients
                .parallelStream()
                .forEach { clientDto -> addClient(clientDto) }

    override fun deleteClient(uuid: UUID) =
            clientRepository.deleteById(uuid)

    @PostConstruct
    private fun init() {
        if(      dumpConfiguration == null||
                !dumpConfiguration?.needed!! ||
                 dumpConfiguration.path.isEmpty())
            return;

        val clientsDataImporter = ClientsDataImporter()
        val clients = clientsDataImporter.loadFromXml(dumpConfiguration.path)
        this.addClients(clients)
    }
}