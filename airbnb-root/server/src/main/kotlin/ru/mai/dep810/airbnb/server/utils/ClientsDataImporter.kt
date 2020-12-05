package ru.mai.dep810.airbnb.server.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.mai.dep810.airbnb.server.dto.ClientDto
import ru.mai.dep810.airbnb.server.dto.StackOverflowUser
import ru.mai.dep810.airbnb.server.dto.toClientDto
import java.io.File
import kotlin.streams.toList


interface IClientsDataImporter{
    fun loadFromXml(path: String):List<ClientDto>
}

class ClientsDataImporter : IClientsDataImporter {

    override fun loadFromXml(path: String): List<ClientDto> {
        val fileWithUsersData = File(path)
        var clients = emptyList<ClientDto>()

        if(!fileWithUsersData.exists())
            return clients;

        val textWithUsersData = fileWithUsersData.readText()
        val xmlMapper = XmlMapper()
                .registerKotlinModule()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        val stackOverflowUsers = xmlMapper.readValue<List<StackOverflowUser>>(textWithUsersData)



        clients = stackOverflowUsers
                .parallelStream()
                .map { user -> user.toClientDto() }
                .toList()

        return clients
    }
}