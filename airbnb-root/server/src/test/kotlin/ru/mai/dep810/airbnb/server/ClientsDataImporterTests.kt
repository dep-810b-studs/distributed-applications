package ru.mai.dep810.airbnb.server

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.mai.dep810.airbnb.server.Constants.Companion.DATE_TIME_FORMAT
import ru.mai.dep810.airbnb.server.dto.ClientDto
import ru.mai.dep810.airbnb.server.utils.ClientsDataImporter

class ClientsDataImporterTests{

    @Test
    fun clientsShouldBeCorrectLoadedFromXml(){
        //arrange
        val clientsDataImporter = ClientsDataImporter()
        val expectedClients = listOf(ClientDto("stanislav", DATE_TIME_FORMAT.parse("2010-10-10 12:53:27.670")),
        ClientDto("vycheslav", DATE_TIME_FORMAT.parse("2020-10-31 21:20:50.183")))
        //act
        val actualClients = clientsDataImporter.loadFromXml("./resources/Users.xml")
        //assert
        assertEquals(expectedClients, actualClients)
    }
}