package ru.mai.dep810.airbnb.data

import java.util.*

enum class ReservationStatus {Reserved, NotReserved}

data class Client(
        var id : UUID,
        var name: String
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