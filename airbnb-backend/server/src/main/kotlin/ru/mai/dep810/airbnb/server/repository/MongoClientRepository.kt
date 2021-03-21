package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.repository.CrudRepository
import java.util.*
import ru.mai.dep810.airbnb.server.data.Client

interface MongoClientRepository : CrudRepository<Client, UUID> {
    fun findTop10ByOrderByCreationDateDesc() : List<Client>
}
