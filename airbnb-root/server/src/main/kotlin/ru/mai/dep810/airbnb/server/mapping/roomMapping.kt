package ru.mai.dep810.airbnb.server.mapping

import org.apache.commons.csv.CSVRecord
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.dto.RoomDto
import java.util.*

fun RoomDto.toDataModel() : Room = Room(
        id = UUID.randomUUID(),
        name = this.name,
        description = this.description,
        neighborhoodOverview = this.neighborhoodOverview,
        location = this.location,
        about = this.about,
        neighbourhood = this.neighbourhood,
        type = this.type ,
        price = this.price,
        reviewsPerMonth = this.reviewsPerMonth
)

fun Room.toDtoModel() : RoomDto = RoomDto(
        name = this.name,
        description = this.description,
        neighborhoodOverview = this.neighborhoodOverview,
        location = this.location,
        about = this.about,
        neighbourhood = this.neighbourhood,
        type = this.type ,
        price = this.price,
        reviewsPerMonth = this.reviewsPerMonth
)

fun CSVRecord.toRoomDtoModel() : RoomDto = RoomDto(
        name = this["name"],
        description = this["description"],
        neighborhoodOverview = this["neighborhood_overview"],
        location = this["host_location"],
        about = this["host_about"],
        neighbourhood = this["host_neighbourhood"],
        type = this["room_type"],
        price = this["price"],
        reviewsPerMonth = this["reviews_per_month"]
)
