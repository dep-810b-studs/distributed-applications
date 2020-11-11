package ru.mai.dep810.airbnb.server.data

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

enum class ReservationStatus {Reserved, NotReserved}

@Document("Clients")
data class Client(
        var id : String,
        var name: String,
        var creationDate: Date
)

@Document("Rooms")
data class Room(
        var id : UUID,
        var name: String,
        var description: String,
        var neighborhoodOverview: String,
        var location: String,
        var about: String,
        var neighbourhood: String,
        var type: String,
        var price: String,
        var reviewsPerMonth: String
)

data class Reservation(
        var roomId: UUID,
        var clientId: UUID,
        var reservationDate: Date,
        var reservationStatus: ReservationStatus
)