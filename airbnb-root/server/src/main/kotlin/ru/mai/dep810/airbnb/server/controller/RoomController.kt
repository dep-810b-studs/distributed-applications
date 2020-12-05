package ru.mai.dep810.airbnb.server.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.service.RoomService
@RestController
@RequestMapping("/api/v1/rooms")
class RoomController {
    @Autowired
    private lateinit var roomService : RoomService

    @GetMapping
    fun top3Rooms() : List<RoomDto> =
            roomService.getTop3Rooms()

    @PostMapping
    fun addRoom(@RequestBody roomDto: RoomDto) =
            roomService.addRoom(roomDto)
}