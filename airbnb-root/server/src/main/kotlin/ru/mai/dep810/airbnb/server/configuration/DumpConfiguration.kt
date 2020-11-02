package ru.mai.dep810.airbnb.server.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "dump")
class DumpConfiguration{
    var needed : Boolean? = null
    var path: String = ""
}
