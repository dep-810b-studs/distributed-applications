package ru.mai.spark.elastic

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark
import java.util.*

interface ISparkTransferElastic{
    fun loadRoomsFromCsvToElastic()
    fun loadClientsFromXmlToElastic()
}

class SparkTransferElastic(private val transferConfiguration: TransferConfiguration) : ISparkTransferElastic {

    private val connectionString: String

    init {
        connectionString = "${transferConfiguration.host}:${transferConfiguration.port.toString()}"
    }

    override fun loadRoomsFromCsvToElastic() {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader for Elastic")
        sparkConf.setMaster("local[${transferConfiguration.flows}]")
        sparkConf.set("es.nodes", connectionString )

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate
        val columnsAr = arrayOf("id","name","description","neighborhood_overview","host_location","host_about",
            "host_neighbourhood",
            "room_type",
            "price",
            "reviews_per_month")

        val superhero = columnsAr.map{ functions.col(it) }.toTypedArray()

        val rdd: JavaRDD<Map<String, String>> = sesBuild.read()
            .format("csv")//ஸ
            .option("sep","ஸ")
            .option("header","true")
            .option("quote", "\"")
            .option("escape","\\")
            .option("multiline",true)
            .option("inferSchema", "true")
            .load(transferConfiguration.roomsPath)
            .select(*superhero)
            .withColumnRenamed("neighborhood_overview", "neighborhoodOverview")
            .withColumnRenamed("host_location", "location")
            .withColumnRenamed("host_about","about")
            .withColumnRenamed("room_type","type")
            .withColumnRenamed("reviews_per_month","reviewsPerMonth")
            .withColumnRenamed("host_neighbourhood","neighbourhood")
            .withColumn("price", functions.expr("substring(price, 2, length(price))"))
            .toJavaRDD()
            .map { row ->
                (columnsAr.indices)
                    .map { index -> columnsAr[index] to row.getString(index) }
                    .associateBy({ it.first }, { it.second })
                    .toMap()
            }
            .filter { row -> (row["id"]?.length ?: 0) < 50 }

        JavaEsSpark.saveToEs(rdd, transferConfiguration.index, Collections.singletonMap("es.mapping.id", "id"))
        sesBuild.stop()
    }

    override fun loadClientsFromXmlToElastic() {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark XML loader for Elastic")
        sparkConf.setMaster("local[${transferConfiguration.flows}]")
        sparkConf.set("es.nodes", connectionString )
        sparkConf.set("es.http.timeout", "5m")
        sparkConf.set("es.http.retries", "10")
        sparkConf.set("es.batch.write.retry.count", "10")
        sparkConf.set("es.batch.write.retry.wait", "30s")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate


        val rdd: JavaRDD<Map<String, *>> = sesBuild
            .read()
            .format("com.databricks.spark.xml")
            .option("rowTag","row")
            .load(transferConfiguration.clientsPath)
            .toJavaRDD()
            .map{row-> mapOf("name" to row.getAs<String>("_DisplayName"),
                "creationDate" to row.getAs<Date>("_CreationDate"),
                "_id" to UUID.randomUUID().toString())
            }

        JavaEsSpark.saveToEs(rdd, transferConfiguration.index, Collections.singletonMap("es.mapping.id", "_id"))
        sesBuild.stop()
    }
}
