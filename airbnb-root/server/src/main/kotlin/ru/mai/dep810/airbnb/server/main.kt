package ru.mai.dep810.airbnb.server

import com.mongodb.client.MongoClients
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import ru.mai.dep810.airbnb.server.service.ClientService
import ru.mai.dep810.airbnb.server.utils.ClientsDataImporter
import javax.annotation.PostConstruct


@SpringBootApplication
class AirBnBApplication{

}

fun main(args: Array<String>) {
    runApplication<AirBnBApplication>(*args)
}

