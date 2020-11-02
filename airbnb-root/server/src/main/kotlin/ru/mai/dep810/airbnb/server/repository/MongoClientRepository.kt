package ru.mai.dep810.airbnb.server.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*
import ru.mai.dep810.airbnb.server.data.Client

interface IClientRepository{
    fun findAll() : List<Client>
    fun save(client: Client): Client
    fun deleteById(uuid: UUID)
}
@Repository("clientRepository")
class MongoClientRepository(val mongoOperations: MongoOperations) : IClientRepository {
    override fun findAll(): List<Client> {
        return mongoOperations.findAll(Client::class.java)
    }

    override fun save(client: Client): Client =
            mongoOperations.save(client)

    override fun deleteById(uuid: UUID) {
        mongoOperations.remove(Query.query(Criteria.where("_id").`is`(uuid)), Client::class.java)
    }
}
