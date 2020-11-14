package ru.mai.dep810.airbnb.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.data.Reservation
import ru.mai.dep810.airbnb.server.data.ReservationStatus
import ru.mai.dep810.airbnb.server.repository.ReservationMongoRepository
import java.util.*

interface IReservationService{
    fun getReserveInfo(reservationId: UUID) : Reservation
    fun getRoomReservations(roomId: UUID) : List<Reservation>
    fun getClientReservations(clientId: UUID) : List<Reservation>
    fun getAllReservations() : List<Reservation>
    fun reserveRoom(reservation: Reservation) : Reservation
    fun unReserveRoom(reservationId: UUID)
    fun reserveRooms(reservations: List<Reservation>)
    fun payForReservation(reservationId: UUID, amount: Float) : Reservation
}

@Service("reservationService")
class ReservationService : IReservationService{

    @Autowired
    private lateinit var reservationRepository: ReservationMongoRepository
    override fun getReserveInfo(reservationId: UUID): Reservation =
            reservationRepository
                    .findById(reservationId)
                    .get()

    override fun getRoomReservations(roomId: UUID): List<Reservation> =
            reservationRepository
                    .findAllByRoomId(roomId)

    override fun getClientReservations(clientId: UUID): List<Reservation> =
            reservationRepository
                    .findAllByClientId(clientId)

    override fun getAllReservations(): List<Reservation> =
            reservationRepository
                    .findAll()
                    .toList()

    override fun reserveRoom(reservation: Reservation) : Reservation {
        reservation.reservationStatus = ReservationStatus.Reserved
        return reservationRepository.save(reservation)
    }

    override fun unReserveRoom(reservationId: UUID) =
            reservationRepository.deleteById(reservationId)

    override fun reserveRooms(reservations: List<Reservation>) {
        reservations
                .parallelStream()
                .forEach { reservation -> reservation.reservationStatus = ReservationStatus.Reserved }
        reservationRepository.saveAll(reservations)
    }

    override fun payForReservation(reservationId: UUID, amount: Float) : Reservation {
        val reservation = reservationRepository.findById(reservationId).get()
        reservation.reservationStatus = ReservationStatus.Paid
        return reservationRepository.save(reservation)
    }

}