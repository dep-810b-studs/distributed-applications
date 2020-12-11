package ru.mai.dep810.airbnb.server.service

/*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.configuration.DumpClientsConfiguration
import ru.mai.dep810.airbnb.server.configuration.DumpMongoConfiguration
import ru.mai.dep810.airbnb.server.configuration.DumpRoomsConfiguration
import spark.sparkTransfer.SparkTransfer
import javax.annotation.PostConstruct



@Service("dumpService")
class DumpService {

    @Autowired
    private lateinit var dumpConfigurationClients: DumpClientsConfiguration
    @Autowired
    private lateinit var dumpConfigurationRooms: DumpRoomsConfiguration
    @Autowired
    private lateinit var dumpMongoConfiguration: DumpMongoConfiguration

    @PostConstruct
    private fun init() {
        if(
                !dumpConfigurationRooms?.needed!! ||
                dumpConfigurationRooms.path.isEmpty() &&  !dumpConfigurationClients?.needed!! ||
                dumpConfigurationClients.path.isEmpty())
            return;

        SparkTransfer.loadFromXmlMongo(dumpConfigurationClients.path, dumpMongoConfiguration.database, dumpMongoConfiguration.host, dumpMongoConfiguration.port, dumpConfigurationClients.flows)
        SparkTransfer.loadFromCsvMongo(dumpConfigurationRooms.path, dumpMongoConfiguration.database, dumpMongoConfiguration.host, dumpMongoConfiguration.port, dumpConfigurationRooms.flows)
        //val clientsDataImporter = ClientsDataImporter()
        //val clients = clientsDataImporter.loadFromXml(dumpConfiguration.path)
        //this.addClients(clients)


    }

}
*/

