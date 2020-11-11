package ru.mai.dep810.airbnb.server.repository

import org.springframework.stereotype.Repository
import ru.mai.dep810.airbnb.server.data.Client
import java.time.Instant
import java.util.*

@Repository("inMemoryClientRepository")
class InMemoryClientRepository : IClientRepository {
    private val clients: MutableMap<String, Client> = listOf(
            Client(UUID.randomUUID().toString(),"Andru", Date()),
            Client(UUID.randomUUID().toString(),"Antony", Date())
    )
            .associateBy { client -> client.id }
            .toMutableMap()

    override fun findAll(): List<Client> =
            clients.values.toList()

    override fun save(client: Client): Client {
        clients[client.id] = client
        return client
    }

    override fun deleteById(uuid: UUID) {
        TODO("Not yet implemented")
    }
}