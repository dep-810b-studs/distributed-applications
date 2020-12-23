package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.mongodb.repository.Query
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.data.RoomElastic
import java.util.*



interface ElasticRoomRepository : ElasticsearchRepository<RoomElastic, String> {

    //fun findAllByNeighborhoodOverviewAndNeighbourhoodAndDescriptionAndLocation(query: String): List<Room>

    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    fun findByName(name: String): List<RoomElastic>
}