package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.mongodb.repository.Query
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.data.RoomElastic
import java.util.*



interface ElasticRoomRepository : ElasticsearchRepository<RoomElastic, String> {

    fun findTop100ByName(name: String): List<RoomElastic>

    fun findTop100ByDescriptionOrLocationOrNeighborhoodOverview(description:String,location: String, neighborhoodOverview:String): List<RoomElastic>

    fun findTop100ByPriceIsLessThan(price:String): List<RoomElastic>
}