package ru.mai.spark.mongo

import com.mongodb.spark.MongoSpark
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.callUDF
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.DataTypes
import java.io.File
import java.io.FileInputStream
import java.util.*
import org.bson.Document
import java.util.*

//import java.util.*


interface ISparkTransferMongo{

    //fun loadFromCsvElastic(path: String, elasticPort: String, elasticHost: String, flows: Int,index: String)
    //fun loadFromXmlElastic(path: String, elasticPort: String, elasticHost: String, flows: Int,index: String)
    fun loadFromCsvMongo(path: String, database: String,host: String,port: Int,flows: Int)
    fun loadFromXmlMongo(path: String, database:String, host:String, port:Int, flows:Int)

}

object SparkTransferMongo : ISparkTransferMongo {

    /*override fun loadFromCsvElastic(path: String, elasticPort: String, elasticHost: String, flows: Int, index: String) {
        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader for Elastic")
        sparkConf.setMaster("local[$flows]")
        sparkConf.set("es.http.timeout", "5m")
        sparkConf.set("es.nodes", "$elasticHost:${elasticPort.toString()}" )
        sparkConf.set("es.http.retries", "10")
        sparkConf.set("es.batch.write.retry.count", "10")
        sparkConf.set("es.batch.write.retry.wait", "30s")
        sparkConf.set("es.index.auto.create", "true")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate


        val columnsAr = arrayOf("id","name","description","neighborhood_overview","host_location","host_about",
                "host_neighbourhood",
                "room_type",
                "price",
                "reviews_per_month")

        val superhero = columnsAr.map{ col(it) }.toTypedArray()

        val rdd: JavaRDD<Row> = sesBuild.read()
                .format("csv")//ஸ
                .option("sep","ஸ")
                .option("header","true")
                .option("quote", "\"")
                .option("escape","\\")
                .option("multiline",true)
                .option("inferSchema", "true")
                .load(path)
                //.withColumn("id", lit(1))
                //.withColumn("id",udfRun())
                .select(*superhero)
                .withColumnRenamed("id","_id")
                .toJavaRDD()




        JavaEsSpark.saveToEs(rdd, index, Collections.singletonMap("es.mapping.id", "_id"))
        sesBuild.stop()



    }

    override fun loadFromXmlElastic(path: String, elasticPort: String, elasticHost: String, flows: Int, index:String) {

            val sparkConf: SparkConf = SparkConf().setAppName("Spark XML loader for Elastic")
            sparkConf.setMaster("local[$flows]")
            sparkConf.set("es.nodes", "$elasticHost:${elasticPort.toString()}" )
            sparkConf.set("es.http.timeout", "5m")
            sparkConf.set("es.http.retries", "10")
            sparkConf.set("es.batch.write.retry.count", "10")
            sparkConf.set("es.batch.write.retry.wait", "30s")
            //sparkConf.set("spark.mongodb.output.shardKey", "id")

            val sesBuild = SparkSession.builder().config(sparkConf).orCreate


            val rdd: JavaRDD<Document>  = sesBuild
                    .read()
                    .format("com.databricks.spark.xml")
                    .option("rowTag","row")
                    .load(path)
                    .toJavaRDD()
                    .map{row-> Document(mapOf("name" to row.getAs<String>("_DisplayName"),
                            "creationDate" to row.getAs<Date>("_CreationDate"),
                            "id" to UUID.randomUUID().toString())
                    )
                    }

            JavaEsSpark.saveToEs(rdd, index, Collections.singletonMap("es.mapping.id", "_id"))
            sesBuild.stop()

    }*/
    override fun loadFromXmlMongo(path: String, database:String, host:String, port:Int, flows:Int) {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark XML loader for Mongo")
        sparkConf.setMaster("local[$flows]")
        sparkConf.set("spark.mongodb.output.uri","mongodb://$host:$port/$database.Clients")
        sparkConf.set("spark.mongodb.output.database","$database")
        sparkConf.set("spark.mongodb.output.collection","Clients")
        sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate


        val rdd: JavaRDD<Document>  = sesBuild
                .read()
                .format("com.databricks.spark.xml")
                .option("rowTag","row")
                .load(path)
                .toJavaRDD()
                .map{row-> Document(mapOf("name" to row.getAs<String>("_DisplayName"),
                        "creationDate" to row.getAs<Date>("_CreationDate"),
                        "_id" to UUID.randomUUID().toString())
                )
                 }


        MongoSpark.save(rdd)
        sesBuild.stop()



    }



    override fun loadFromCsvMongo(path: String, database: String, host: String, port: Int, flows: Int)  {


        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader for Mongo")
            sparkConf.setMaster("local[$flows]")
            sparkConf.set("spark.mongodb.output.uri","mongodb://$host:$port/$database.Rooms")
            sparkConf.set("spark.mongodb.output.database","$database")
            sparkConf.set("spark.mongodb.output.collection","Rooms")
            sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder()
                .config(sparkConf)
                .orCreate

        val columnsAr = arrayOf("id","name","description","neighborhood_overview","host_location","host_about",
                "host_neighbourhood",
                "room_type",
                "price",
                "reviews_per_month")

        val superhero = columnsAr.map{ col(it) }.toTypedArray()

        val df: Dataset<Row> = sesBuild.read()
                .format("csv")//ஸ
                .option("sep","ஸ")
                .option("header","true")
                .option("quote", "\"")
                .option("escape","\\")
                .option("multiline",true)
                .option("inferSchema", "true")
                .load(path)
                //.withColumn("id", lit(1))
                //.withColumn("id",udfRun())
                .select(*superhero)
                .withColumnRenamed("id","_id")


        MongoSpark.save(df)

        sesBuild.stop()

    }

}


fun main(args: Array<String>) {

    val file = File("D:/GIT/distributed-applications/airbnb-root/sparkTransferMongo/src/main/resources/application.properties")

    val prop = Properties()
    FileInputStream(file).use { prop.load(it) }

    SparkTransferMongo.loadFromXmlMongo(prop.getProperty("dump.clients.path"),
            prop.getProperty("mongo.database"),
            prop.getProperty("mongo.host"),
            prop.getProperty("mongo.port").toInt(),
            prop.getProperty("dump.clients.flows").toInt())

    SparkTransferMongo.loadFromCsvMongo(prop.getProperty("dump.rooms.path"),
            prop.getProperty("mongo.database"),
            prop.getProperty("mongo.host"),
            prop.getProperty("mongo.port").toInt(),
            prop.getProperty("dump.rooms.flows").toInt())

}

    /* SparkTransfer.loadFromCsvElastic(prop.getProperty("dump.rooms.path"),
            prop.getProperty("elastic.port"),
            prop.getProperty("elastic.host"),
            prop.getProperty("elastic.flows").toInt(),
            prop.getProperty("elastic.rooms.index")
    )

            /*SparkTransfer.loadFromXmlElastic(prop.getProperty("dump.clients.path"),
            prop.getProperty("elastic.port"),
            prop.getProperty("elastic.host"),
            prop.getProperty("elastic.flows").toInt(),
            prop.getProperty("elastic.clients.index")
    )*/