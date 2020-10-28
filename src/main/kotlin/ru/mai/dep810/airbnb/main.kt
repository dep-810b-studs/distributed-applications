package ru.mai.dep810.airbnb

import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import java.io.FileInputStream



@SpringBootApplication(exclude= arrayOf(MongoAutoConfiguration::class,MongoDataAutoConfiguration::class))
class AirBnBApplication{
	@Bean()
	fun mongoOperations() : MongoOperations =

		 MongoTemplate(MongoClients.create("mongodb://localhost:27018"), "tempr")
}



fun main(args: Array<String>) {

	runApplication<AirBnBApplication>(*args)
}
