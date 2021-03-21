package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.repository.CrudRepository
import ru.mai.dep810.airbnb.server.data.Reservation
import java.util.*

interface ReservationMongoRepository : CrudRepository<Reservation, UUID> {
    fun findAllByRoomId(roomId: String) : List<Reservation>
    fun findAllByClientId(clientId: UUID) : List<Reservation>
}