package ru.mai.dep810.airbnb.server.utils

import org.springframework.context.annotation.Bean
import ru.mai.dep810.airbnb.server.data.Client
import ru.mai.dep810.airbnb.server.data.Reservation
import ru.mai.dep810.airbnb.server.data.ReservationStatus
import ru.mai.dep810.airbnb.server.data.Room
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID.*
import kotlin.random.Random

interface IReservationsGenerator {
    fun makeRandomReservations(clients: List<Client>, rooms: List<Room>) : List<Reservation>
}

class ReservationsGenerator : IReservationsGenerator{
    override fun makeRandomReservations(clients: List<Client>, rooms: List<Room>): List<Reservation> {
        val reservations = mutableListOf<Reservation>()
        val reservationsCount = if (clients.size < rooms.size) clients.size else rooms.size

        for (i in 0 until reservationsCount) {
            val daysDelta =  Random.nextLong(30)
            val daysCount = Random.nextLong(10)
            val reservation = Reservation(
                    randomUUID(),
                    rooms[i].id,
                    fromString(clients[i]._id),
                    reservationStartDate = Instant.now().plus(daysDelta, ChronoUnit.DAYS),
                    reservationEndDate = Instant.now().plus(daysDelta + daysCount, ChronoUnit.DAYS),
                    reservationStatus = ReservationStatus.Paid
            )
            reservations.add(reservation)
        }

        return reservations
    }

}