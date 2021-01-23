package ru.mai.spark.mongo

import com.mongodb.spark.MongoSpark
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.bson.Document
import java.util.*

interface ISparkTransferMongo{
    fun loadRoomsFromCsvToMongo()
    fun loadClientsFromXmlToMongo()
}

class SparkTransferMongo(private val transferConfiguration: TransferConfiguration) : ISparkTransferMongo {

    private val connectionString: String

    init {
        connectionString = "mongodb://${transferConfiguration.host}:${transferConfiguration.port}/${transferConfiguration.database}"
    }

    override fun loadRoomsFromCsvToMongo() {
        val sparkConf: SparkConf = SparkConf().setAppName("Spark XML loader for Mongo")
        sparkConf.setMaster("local[${transferConfiguration.flows}]")
        sparkConf.set("spark.mongodb.output.uri","${connectionString}.Clients")
        sparkConf.set("spark.mongodb.output.database","${transferConfiguration.database}")
        sparkConf.set("spark.mongodb.output.collection","Clients")
        sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate

        val rdd: JavaRDD<Document> = sesBuild
                .read()
                .format("com.databricks.spark.xml")
                .option("rowTag","row")
                .load(transferConfiguration.path)
                .toJavaRDD()
                .map{row-> Document(mapOf("name" to row.getAs<String>("_DisplayName"),
                        "creationDate" to row.getAs<Date>("_CreationDate"),
                        "_id" to UUID.randomUUID().toString())) }

        MongoSpark.save(rdd)
        sesBuild.stop()
    }

    override fun loadClientsFromXmlToMongo()  {
        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader for Mongo")
        sparkConf.setMaster("local[${transferConfiguration.flows}]")
        sparkConf.set("spark.mongodb.output.uri","mongodb://${connectionString}.Rooms")
        sparkConf.set("spark.mongodb.output.database","${transferConfiguration.database}")
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

        val superhero = columnsAr.map{ functions.col(it) }.toTypedArray()

        val df: Dataset<Row> = sesBuild.read()
                .format("csv")//ஸ
                .option("sep","ஸ")
                .option("header","true")
                .option("quote", "\"")
                .option("escape","\\")
                .option("multiline",true)
                .option("inferSchema", "true")
                .load(transferConfiguration.path)
                //.withColumn("id", lit(1))
                //.withColumn("id",udfRun())
                .select(*superhero)
                .withColumnRenamed("id","_id")
                .withColumnRenamed("neighborhood_overview", "neighborhoodOverview")
                .withColumnRenamed("host_location", "location")
                .withColumnRenamed("host_about","about")
                .withColumnRenamed("room_type","type")
                .withColumnRenamed("reviews_per_month","reviewsPerMonth")
                .withColumnRenamed("host_neighbourhood","neighbourhood")
                .withColumn("price", functions.expr("substring(price, 2, length(price))"))

        MongoSpark.save(df)
        sesBuild.stop()
    }
}