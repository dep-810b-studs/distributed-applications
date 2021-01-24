package ru.mai.spark.elastic

import java.io.File
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {

    if(args.size < 2) {
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

    var transferConfiguration = TransferConfiguration()

    try {
        transferConfiguration = prop.toTransferConfiguration()
    }
    catch (ex: NullPointerException){
        println("Program can find some needed field in configuration file. ${ex.localizedMessage}")
        return;
    }

    val sparkTransferMongo = SparkTransferElastic(transferConfiguration)
    val entityToTransfer = args[1]

    when(entityToTransfer){
        "clients" -> sparkTransferMongo.loadClientsFromXmlToElastic()
        "rooms" -> sparkTransferMongo.loadRoomsFromCsvToElastic()
        else -> println("This option (${entityToTransfer}) is not supported")
    }
}