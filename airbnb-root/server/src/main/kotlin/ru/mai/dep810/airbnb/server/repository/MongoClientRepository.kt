package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import ru.mai.dep810.airbnb.server.data.Client


interface MongoClientRepository : CrudRepository<Client, UUID> {
    fun findTop10ByOrderByCreationDateDesc() : List<Client>
}
