package ru.mai.dep810.airbnb.controller

import org.springframework.web.bind.annotation.*
import ru.mai.dep810.airbnb.dto.ClientDto
import ru.mai.dep810.airbnb.service.ClientService
import java.util.*

@RestController
@RequestMapping("/api/clients")
class ClientController(val clientService: ClientService) {
    @GetMapping
    fun allClients() : List<ClientDto> =
            clientService.getAllClients()

    @PostMapping
    fun addClient(@RequestBody clientDto: ClientDto) : ClientDto =
            clientService.addClient(clientDto.copy())

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: UUID) =
            clientService.deleteClient(id)
}