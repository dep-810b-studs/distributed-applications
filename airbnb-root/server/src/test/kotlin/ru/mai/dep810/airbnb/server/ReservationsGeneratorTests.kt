package ru.mai.dep810.airbnb.server

import org.junit.jupiter.api.Test
import ru.mai.dep810.airbnb.server.data.Client
import ru.mai.dep810.airbnb.server.data.Reservation
import ru.mai.dep810.airbnb.server.data.ReservationStatus
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.utils.ReservationsGenerator
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals


class ReservationsGeneratorTests {
    @Test
    fun `ReservationGenerator Should generate reservations with count  len clients`(){
        //arrange
        val firstClient = getFakeClient()
        val secondClient = getFakeClient()
        val clients = listOf(firstClient,secondClient)
        val firstRoom = getFakeRoom()
        val secondRoom = getFakeRoom()
        val thirdRoom = getFakeRoom()
        val rooms = listOf(firstRoom, secondRoom, thirdRoom)
        val reservationsGenerator = ReservationsGenerator()

        //act
        val actualReservations = reservationsGenerator.makeRandomReservations(clients, rooms)

        //assert
        assertEquals(2, actualReservations.size)

        assertEquals(firstClient.id.toString(), actualReservations[0].clientId.toString())
        assertEquals(secondClient.id.toString(), actualReservations[1].clientId.toString())

        assertEquals(firstRoom.id.toString(), actualReservations[0].roomId.toString())
        assertEquals(secondRoom.id.toString(), actualReservations[1].roomId.toString())
    }

    private fun getFakeClient() : Client =
            Client(UUID.randomUUID().toString(),"", Date.from(Instant.now()))

    private fun getFakeRoom() : Room =
            Room(UUID.randomUUID(),"","","","","","","", "","" )

    private fun getFakeReservation(roomId: UUID, clientId: String) : Reservation =
            Reservation(UUID.randomUUID(),roomId, UUID.fromString(clientId), Instant.now(),Instant.now(), ReservationStatus.Paid)
}