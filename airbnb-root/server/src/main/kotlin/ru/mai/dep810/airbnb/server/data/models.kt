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

data class Address(
        var country: String,
        var city: String,
        var street: String,
        var homeNumber: Int,
        var floorNumber: Int,
        var flatNumber: Int
)

data class Room(
        var id : UUID,
        var address: Address,
        var description: String,
        var attributes: List<String>
)

data class Reservation(
        var roomId: UUID,
        var clientId: UUID,
        var reservationDate: Date,
        var reservationStatus: ReservationStatus
)