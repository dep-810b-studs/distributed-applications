package ru.mai.spark.elastic

import ru.mai.spark.mongo.SparkTransferMongo
import java.io.File
import java.io.FileInputStream
import java.lang.IllegalArgumentException
import java.util.*

fun main(args: Array<String>) {

    if(args.size < 3) {
        println("There is not enough command line arguments. Program cant work");
        return;
    }

    val fileWithPropertiesName = args[0];
    val fileWithProperties = File(fileWithPropertiesName)

    if(!fileWithProperties.exists()){
        println("File with properties ${fileWithProperties.absoluteFile} doesnt exist. Program cant work");
        return;
    }

    val prop = Properties();

    try{
        FileInputStream(fileWithProperties).use { prop.load(it) }
    }
    catch(ex: Exception){
        println("File with properties is not correct. ${ex.localizedMessage}. Program cant work")
    }

    var transferConfiguration: TransferConfiguration

    try {
        transferConfiguration = prop.toTransferConfiguration()
    }
    catch (ex: NullPointerException){
        println("Program can find some needed field in configuration file. ${ex.localizedMessage}")
        return;
    }

    val transferTarget = args[1]

    val sparkExporter = when(transferTarget){
        "mongo" -> SparkTransferMongo(transferConfiguration)
        "elastic" -> SparkTransferElastic(transferConfiguration)
        else -> throw IllegalArgumentException("This transfer target(${transferTarget}) its not supported.")
    }
    val entityToTransfer = args[2]

    when(entityToTransfer){
        "clients" -> sparkExporter.loadClientsFromXml()
        "rooms" -> sparkExporter.loadRoomsFromCsv()
        else -> println("This option (${entityToTransfer}) is not supported")
    }
}