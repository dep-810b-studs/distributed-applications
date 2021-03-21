package ru.mai.dep810.airbnb.server.data

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*
import org.springframework.data.annotation.Id

enum class ReservationStatus {NotReserved, Reserved, Paid}

@Document("Clients")
data class Client(
        var _id : String,
        var name: String,
        var creationDate: String
)

@Document("Rooms")
data class Room(
        var id : String,
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

@Document("Reservations")
data class Reservation(
        var id : UUID ,
        var roomId: String,
        var clientId: UUID,
        var reservationStartDate: Instant,
        var reservationEndDate: Instant,
        var reservationStatus: ReservationStatus
)