package ru.mai.spark.elastic

import java.util.*

data class TransferConfiguration (
    var clientsPath: String = "",
    var roomsPath: String = "",
    var elasticHost: String = "",
    var index: String = "",
    var elasticPort: Int = 0,
    var mongoDatabase: String = "",
    var nongoHost: String = "",
    var mongoPort: Int = 0,
    var flows: Int = 0
)

fun Properties.toTransferConfiguration() : TransferConfiguration =
    TransferConfiguration(
        this.getProperty("dump.clients.path"),
        this.getProperty("dump.rooms.path"),
        this.getProperty("elastic.host"),
        this.getProperty("elastic.rooms.index"),
        this.getProperty("elastic.port").toInt(),
        this.getProperty("mongo.database"),
        this.getProperty("mongo.host"),
        this.getProperty("mongo.port").toInt(),
        this.getProperty("dump.flows").toInt()
    )