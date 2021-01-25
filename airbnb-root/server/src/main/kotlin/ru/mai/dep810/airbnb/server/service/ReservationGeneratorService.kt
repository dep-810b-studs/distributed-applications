package ru.mai.dep810.airbnb.server.service

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.repository.MongoClientRepository
import ru.mai.dep810.airbnb.server.repository.MongoRoomRepository
import ru.mai.dep810.airbnb.server.repository.ReservationMongoRepository
import ru.mai.dep810.airbnb.server.utils.ReservationsGenerator
import javax.annotation.PostConstruct

@Service
class ReservationGeneratorService{
    private val logger = LoggerFactory.getLogger(ReservationGeneratorService::class.java)
    @Autowired
    private lateinit var clientRepository: MongoClientRepository
    @Autowired
    private lateinit var roomRepository: MongoRoomRepository
    @Autowired
    private lateinit var reservationRepository: ReservationMongoRepository
    @Value("\${reservations.generator.needed}")
    var reservationsGeneratorShouldStart: Boolean = false

    @PostConstruct
    private fun init() {
        if(reservationsGeneratorShouldStart){
            logger.info("Reservations generating started...")
            val clients = clientRepository.findAll().toList()
            val rooms = roomRepository.findAll().toList()
            val reservationsGenerator = ReservationsGenerator()
            val reservations = reservationsGenerator.makeRandomReservations(clients, rooms)
            reservationRepository.saveAll(reservations)
            logger.info("Reservations generating finished...")
        }
    }
}