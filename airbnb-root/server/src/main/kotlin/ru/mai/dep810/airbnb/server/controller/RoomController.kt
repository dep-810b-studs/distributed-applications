package ru.mai.dep810.airbnb.server.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.mai.dep810.airbnb.server.data.RoomElastic
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.service.RoomService
import java.util.*

@RestController
@RequestMapping("rooms")
class RoomController {
    @Autowired
    private lateinit var roomService : RoomService

    @GetMapping
    fun top3Rooms() : List<RoomDto> =
            roomService.getTop3Rooms()

    @PostMapping
    fun addRoom(@RequestBody roomDto: RoomDto) =
            roomService.addRoom(roomDto)

    @PostMapping("try")
    fun elasticSearch(@RequestBody text: String): List<RoomElastic> =
            roomService.searchComon(text)

    @GetMapping("textSearch")
    fun allSearchFind(@RequestParam description:String): List<RoomElastic> =
            roomService.findAll(description)

    @GetMapping("priceSearch")
    fun priceSearchFind(@RequestParam text: String): List<RoomElastic> =
            roomService.findAllByPriceIsLessThan(text)




}