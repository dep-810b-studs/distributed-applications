package ru.mai.dep810.airbnb.server.dto

import java.util.*

data class ClientDto(
        var name: String,
        var creationDate: Date
)

data class RoomDto(
        var name: String?,
        var description: String?,
        var neighborhoodOverview: String?,
        var location: String?,
        var about: String?,
        var neighbourhood: String?,
        var type: String?,
        var price: String?,
        var reviewsPerMonth: String?
)
