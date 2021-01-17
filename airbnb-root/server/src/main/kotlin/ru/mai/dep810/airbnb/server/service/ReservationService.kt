package ru.mai.dep810.airbnb.server.service

import com.hazelcast.core.HazelcastInstance
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.data.Reservation
import ru.mai.dep810.airbnb.server.data.ReservationStatus
import ru.mai.dep810.airbnb.server.data.Room
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
class ReservationService(val hazelcastInstance: HazelcastInstance,
                         val reservationRepository: ReservationMongoRepository) : IReservationService{

    val reservationsCache = hazelcastInstance.getMap<UUID, Room>("room")

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
        var reservationResult = reservation
        val lockSuccess = reservationsCache.tryLock(reservation.roomId)
        if(!lockSuccess)
            return reservationResult
        reservation.reservationStatus = ReservationStatus.Reserved
        try{
            Thread.sleep(20000)
            reservationResult = reservationRepository.save(reservation)
        }
        finally {
            reservationsCache.unlock(reservation.roomId)
        }

        return reservationResult
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
        var reservation = reservationRepository.findById(reservationId).get()
        if(reservation.reservationStatus != ReservationStatus.Reserved)
            return reservation

        val lockSuccess = reservationsCache.tryLock(reservation.roomId)
        if(!lockSuccess)
            return reservation

        reservation.reservationStatus = ReservationStatus.Paid

        try{
            Thread.sleep(20000)
            reservation = reservationRepository.save(reservation)
        }
        finally {
            reservationsCache.unlock(reservation.roomId)
        }
        return reservation
    }

    companion object {
        private const val RESERVATION_PENDING_MINUTES = 2L
    }

}