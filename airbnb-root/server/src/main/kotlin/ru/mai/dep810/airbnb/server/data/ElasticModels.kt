package ru.mai.dep810.airbnb.server.data

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.util.*
import ru.mai.dep810.airbnb.server.configuration.DumpElasticConfiguration

@Document(indexName = "airbnb", type = "Rooms")
data class RoomElastic(
        @Id var id : UUID,
        var name: String,
        var description: String,
        var neighborhoodOverview: String,
        var location: String,
        var about: String,
        var neighbourhood: String,
        var type: String,
        var price: String,
        var reviewsPerMonth: Float
)
