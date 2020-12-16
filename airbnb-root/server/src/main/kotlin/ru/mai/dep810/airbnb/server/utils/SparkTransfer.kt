package ru.mai.dep810.airbnb.server.utils

import org.elasticsearch.common.collect.HppcMaps


interface ISparkTransfer{
    fun loadFromXml(path: String):List<HppcMaps.Object.Integer>
}


class SparkTransfer : ISparkTransfer {

    override fun loadFromXml(path: String): List<HppcMaps.Object.Integer> {
        TODO("Not yet implemented")
    }
}