package ru.mai.dep810.airbnb.server.configuration



import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "elastic.rooms")
class DumpElasticConfiguration {
    var index: String? = null
    var type: String? = null
}