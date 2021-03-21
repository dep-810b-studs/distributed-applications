package ru.mai.spark.elastic

interface ISparkExporter {
    fun loadRoomsFromCsv()
    fun loadClientsFromXml()
}