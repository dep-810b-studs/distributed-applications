package ru.mai.dep810.airbnb.server.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.mai.dep810.airbnb.server.data.Reservation
import ru.mai.dep810.airbnb.server.service.IReservationService
import java.util.*

@RestController
@RequestMapping("reservations")
class ReservationController {

    @Autowired
    private lateinit var reservationService : IReservationService

    @GetMapping
    fun reservationsInfo(@RequestParam(required = false) reservationId: UUID?): List<Reservation> {
        return if(reservationId == null) reservationService.getAllReservations()
        else {
            val reservation = reservationService.getReserveInfo(reservationId)
            listOf(reservation)
        }
    }

    @GetMapping("room")
    fun roomReservations(@RequestParam roomId: String): List<Reservation> =
            reservationService.getRoomReservations(roomId)

    @GetMapping("client")
    fun clientReservations(@RequestParam clientId: UUID): List<Reservation> =
            reservationService.getClientReservations(clientId)

    @PostMapping
    fun reserveRoom(@RequestBody reservation: Reservation): Reservation =
            reservationService.reserveRoom(reservation)

    @DeleteMapping
    fun unReserveRoom(@RequestParam reservationId: UUID) =
            reservationService.unReserveRoom(reservationId)

    @PostMapping("paid")
    fun payForReservation(@RequestParam reservationId: UUID, @RequestParam amount: Float = 0F): Reservation =
            reservationService.payForReservation(reservationId, amount)
}