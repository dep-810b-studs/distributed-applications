package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.repository.CrudRepository
import ru.mai.dep810.airbnb.server.data.Room
import java.util.*

interface MongoRoomRepository : CrudRepository<Room, UUID> {
    fun findTop3ByOrderByIdDesc() : List<Room>
}