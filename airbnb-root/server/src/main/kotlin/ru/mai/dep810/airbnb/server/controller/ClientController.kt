package ru.mai.dep810.airbnb.server.controller

import org.springframework.web.bind.annotation.*
import ru.mai.dep810.airbnb.server.data.Client
import ru.mai.dep810.airbnb.server.dto.ClientDto
import ru.mai.dep810.airbnb.server.service.ClientService
import java.util.*

@RestController
@RequestMapping("clients")
class ClientController(val clientService: ClientService) {
    @GetMapping
    fun topClients() : List<Client> =
            clientService.getTopClients()

    @PostMapping
    fun addClient(@RequestBody clientDto: ClientDto) : ClientDto =
         clientService.addClient(clientDto.copy())


    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: UUID) =
            clientService.deleteClient(id)
}

