package ru.mai.dep810.airbnb.server

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.junit.jupiter.api.Test
import ru.mai.dep810.airbnb.server.data.Room
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.mapping.toRoomDtoModel
import ru.mai.dep810.airbnb.server.utils.RoomsDataImporter
import java.io.File
import kotlin.test.assertEquals

class RoomDataImporterTests {
    @Test
    fun `Mapper should correct map CSVRecord to roomDtoModel`(){
        //arrange
        val fileWithRoomData = File("./resources/listings.csv")
        val csvParser = CSVParser(fileWithRoomData.reader(), CSVFormat.DEFAULT.withHeader())
        //act
        val parsedRoomData = csvParser.records.first().toRoomDtoModel()
        //assert
        assertEquals(parsedRoomData, fakeRoomDto)
    }

    @Test
    fun `RoomDataImporter should correct read data from CSV`(){
        //arrange
        val expectedRooms = listOf(fakeRoomDto)
        val roomDataImporter = RoomsDataImporter()
        //act
        val rooms = roomDataImporter.loadFromCsv("./resources/listings.csv")
        //assert
        assertEquals(expectedRooms, rooms)
    }

    private var fakeRoomDto : RoomDto = RoomDto(
            "name",
            "description",
            "neighbourhoodOverview",
            "location",
            "about",
            "neighbourhood",
            "type",
            "price",
            "reviewsPerMonths"
    )
}