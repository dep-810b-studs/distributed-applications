package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import ru.mai.dep810.airbnb.server.data.RoomElastic
import java.util.*



interface ElasticRoomRepository : ElasticsearchRepository<RoomElastic, UUID> {
}