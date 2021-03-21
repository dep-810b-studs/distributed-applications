package ru.mai.dep810.airbnb.server.utils

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import ru.mai.dep810.airbnb.server.dto.ClientDto
import ru.mai.dep810.airbnb.server.dto.RoomDto
import ru.mai.dep810.airbnb.server.mapping.toRoomDtoModel
import java.io.File
import kotlin.streams.toList

interface IRoomsDataImporter{
    fun loadFromCsv(path: String) : List<RoomDto>
}

class RoomsDataImporter : IRoomsDataImporter {
    override fun loadFromCsv(path: String): List<RoomDto> {
        val fileWithRoomData = File(path)

        if(!fileWithRoomData.exists())
            return emptyList<RoomDto>()

        val csvParsers = CSVParser(fileWithRoomData.reader(), CSVFormat.DEFAULT.withHeader())

        val rooms = csvParsers.records
                .parallelStream()
                .map { record ->  record.toRoomDtoModel()}
                .toList()

        return rooms
    }
}