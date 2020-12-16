package ru.mai.dep810.airbnb.server.configuration


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.data.mongodb")
class DumpMongoConfiguration {

    var database: String = ""
    var port : Int = 8000
    var host : String = "localhost"
}