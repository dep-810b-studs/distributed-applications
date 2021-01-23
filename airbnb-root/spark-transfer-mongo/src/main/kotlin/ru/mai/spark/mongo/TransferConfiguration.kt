package ru.mai.spark.mongo

import java.util.*

data class TransferConfiguration (
        var path: String = "",
        var database: String = "",
        var host: String = "",
        var port: Int = 0,
        var flows: Int = 0)

fun Properties.toTransferConfiguration() : TransferConfiguration =
        TransferConfiguration(
                this.getProperty("dump.clients.path"),
                this.getProperty("mongo.database"),
                this.getProperty("mongo.host"),
                this.getProperty("mongo.port").toInt(),
                this.getProperty("dump.clients.flows").toInt()
        )