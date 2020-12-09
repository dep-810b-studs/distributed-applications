package ru.mai.dep810.airbnb.server.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dump.clients")
class DumpClientsConfiguration{
    var needed : Boolean? = null
    var path: String = ""
    var flows: Int = 2
}


