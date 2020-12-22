package ru.mai.dep810.airbnb.server.service

import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryStringQueryBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import ru.mai.dep810.airbnb.server.configuration.DumpMongoConfiguration
import ru.mai.dep810.airbnb.server.configuration.DumpRoomsConfiguration
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.data.RoomElastic
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.mapping.toDataModel
import ru.mai.dep810.airbnb.server.mapping.toDtoModel
import ru.mai.dep810.airbnb.server.mapping.toElasticDataModel
import ru.mai.dep810.airbnb.server.repository.ElasticRoomRepository
import ru.mai.dep810.airbnb.server.repository.MongoRoomRepository
import ru.mai.dep810.airbnb.server.utils.RoomsDataImporter
import java.sql.Struct
import java.util.*
import javax.annotation.PostConstruct
import kotlin.streams.toList

interface IRoomService{
    fun getAllRooms() : List<RoomDto>
    fun addRoom(room: RoomDto) : RoomDto
    fun addRooms(room: List<RoomDto>)
    fun searchComon(text:String):List<RoomDto>
}

@Service("roomService")
class RoomService : IRoomService{

    @Autowired
    private lateinit var roomRepository: MongoRoomRepository

    @Autowired
    private lateinit var roomElasticRepository: ElasticRoomRepository

    @Autowired
    private  lateinit var elasticsearchTemplate: ElasticsearchTemplate



    override fun getAllRooms(): List<RoomDto> =
        roomRepository
                .findAll()
                .map { room -> room.toDtoModel() }




    override fun addRooms(roomsDto: List<RoomDto>) {

        val rooms = roomsDto
                .parallelStream()
                .map{roomDto -> Pair<UUID, RoomDto>(UUID.randomUUID(),roomDto) }
                .map { (val1,val2) -> Pair<Room, RoomElastic>(val2.toDataModel(val1),  val2.toElasticDataModel(val1)) }
                .toList()

        roomRepository.saveAll(rooms.map { (val1,val2) -> val1 }.toList() )
        roomElasticRepository.saveAll(rooms.map { (val1,val2) -> val2 }.toList() )
    }

    override fun addRoom(room: RoomDto): RoomDto {
        val id = UUID.randomUUID()
        roomElasticRepository.save(room.toElasticDataModel(id))
        return roomRepository.save(room.toDataModel(id)).toDtoModel()
    }


    fun getTop3Rooms():List<RoomDto> =
            roomRepository
                    .findTop3ByOrderByIdDesc()
                    .map{room -> room.toDtoModel()}


    override fun searchComon(text:String):List<RoomDto> {

        val nativeSearchQueryBuilder =  NativeSearchQueryBuilder()
        val builder = QueryBuilders.boolQuery()
        builder.must(QueryStringQueryBuilder(text)
                .field("neighbourhood")
                .field("location")
                .field("about")
                .field("description"))
        nativeSearchQueryBuilder.withQuery(builder)
        val query = nativeSearchQueryBuilder.build()
        val blogList : List<RoomDto> = elasticsearchTemplate.queryForList(query, RoomDto.class)



    }




}

