package ru.mai.dep810.airbnb

import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate

@SpringBootApplication
class AirBnBApplication{
	@Bean()
	fun mongoOperations() : MongoOperations =
		return MongoTemplate(MongoClients.create("mongodb://localhost:27017"), "AirBnB")
}

fun main(args: Array<String>) {
	runApplication<AirBnBApplication>(*args)
}
