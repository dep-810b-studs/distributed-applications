package ru.mai.dep810.airbnb.server.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.configuration.DumpRoomsConfiguration
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.mapping.toDataModel
import ru.mai.dep810.airbnb.server.mapping.toDtoModel
import ru.mai.dep810.airbnb.server.repository.MongoRoomRepository
import ru.mai.dep810.airbnb.server.utils.RoomsDataImporter
import javax.annotation.PostConstruct
import kotlin.streams.toList

interface IRoomService{
    fun getAllRooms() : List<RoomDto>
    fun addRoom(room: RoomDto) : RoomDto
    fun addRooms(room: List<RoomDto>)
}

@Service("roomService")
class RoomService : IRoomService{

    @Autowired
    private lateinit var roomRepository: MongoRoomRepository
    @Autowired
    private lateinit var dumpConfiguration: DumpRoomsConfiguration

    override fun getAllRooms(): List<RoomDto> =
        roomRepository
                .findAll()
                .map { room -> room.toDtoModel() }

    override fun addRooms(roomsDto: List<RoomDto>) {
        val rooms = roomsDto
                .parallelStream()
                .map{roomDto -> roomDto.toDataModel()}
                .toList()

        roomRepository.saveAll(rooms)
    }

    override fun addRoom(room: RoomDto): RoomDto =
        roomRepository.save(room.toDataModel()).toDtoModel()


    fun getTop3Rooms():List<RoomDto> =
            roomRepository
                    .findTop3ByOrderByIdDesc()
                    .map{room -> room.toDtoModel()}

    @PostConstruct
    private fun init() {
        if(      dumpConfiguration == null||
                !dumpConfiguration?.needed!! ||
                dumpConfiguration.path.isEmpty())
            return;

        val clientsDataImporter = RoomsDataImporter()
        val rooms = clientsDataImporter.loadFromCsv(dumpConfiguration.path)
        this.addRooms(rooms)
    }
}