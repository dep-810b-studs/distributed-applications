package ru.mai.dep810.twitter.data

import java.util.*

enum class ReservationStatus {Reserved, NotReserved}

data class Client(
        var id : UUID,
        var name: String
)

data class Adress(
        var country: String,
        var city: String,
        var street: String,
        var homeNumber: Int,
        var floorNumber: Int,
        var flatNumber: Int
)

data class Room(
        var id : UUID,
        var adress: Adress,
        var description: String,
        var attributes: List<String>
)

data class Reservation(
        var roomId: UUID,
        var clientId: UUID,
        var reservationDate: Date,
        var reservationStatus: ReservationStatus
)