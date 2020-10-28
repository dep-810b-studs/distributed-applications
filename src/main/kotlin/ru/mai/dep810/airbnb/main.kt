package ru.mai.dep810.airbnb

import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.elasticsearch.client.ElasticsearchClient
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import java.io.FileInputStream



@SpringBootApplication(exclude= arrayOf(MongoAutoConfiguration::class,MongoDataAutoConfiguration::class))
class AirBnBApplication{
	@Bean()
    @Scope("singleton")
	fun mongoOperations() : MongoOperations =

		 MongoTemplate(MongoClients.create("mongodb://localhost:27018"), "tempr")

	@Bean()
	@Scope("singleton")
	fun elasticOperations() : RestHighLevelClient {
		var clientConf: ClientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build()
		return RestClients.create(clientConf).rest()
		//ElasticsearchTemplate(ElasticsearchClient("mongodb://localhost:27018"), "tempr")
	}

}



fun main(args: Array<String>) {

	runApplication<AirBnBApplication>(*args)
}
