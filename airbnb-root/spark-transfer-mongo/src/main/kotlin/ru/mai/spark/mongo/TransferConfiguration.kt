package ru.mai.spark.mongo

import java.util.*

data class TransferConfiguration (
        var clientsPath: String = "",
        var roomsPath: String = "",
        var database: String = "",
        var host: String = "",
        var port: Int = 0,
        var flows: Int = 0)

fun Properties.toTransferConfiguration() : TransferConfiguration =
        TransferConfiguration(
                this.getProperty("dump.clients.path"),
                this.getProperty("dump.rooms.path"),
                this.getProperty("mongo.database"),
                this.getProperty("mongo.host"),
                this.getProperty("mongo.port").toInt(),
                this.getProperty("dump.flows").toInt()
        )