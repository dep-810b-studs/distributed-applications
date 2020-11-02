package ru.mai.dep810.airbnb.server.dto

import java.util.*

enum class ReservationStatusDto {Reserved, NotReserved}

data class ClientDto(
        var name: String,
        var creationDate: Date
)

data class AddressDto(
        var country: String,
        var city: String,
        var street: String,
        var homeNumber: Int,
        var floorNumber: Int,
        var flatNumber: Int
)

data class RoomDto(
        var id : UUID,
        var address: AddressDto,
        var description: String,
        var attributes: List<String>
)

data class ReservationDto(
        var roomId: UUID,
        var clientId: UUID,
        var reservationDate: Date,
        var reservationStatus: ReservationStatusDto
)