package ru.mai.spark.elastic

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.*
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.callUDF
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.DataTypes
import java.io.File
import java.io.FileInputStream
import java.util.*
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark
import java.util.*



interface ISparkTransferElastic{

    fun loadFromCsvElastic(path: String, elasticPort: String, elasticHost: String, flows: Int,index: String)
    fun loadFromXmlElastic(path: String, elasticPort: String, elasticHost: String, flows: Int,index: String)


}

object SparkTransferElastic : ISparkTransferElastic {

    override fun loadFromCsvElastic(path: String, elasticPort: String, elasticHost: String, flows: Int, index: String) {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader for Elastic")
        sparkConf.setMaster("local[$flows]")
        //sparkConf.set("es.http.timeout", "5m")
        sparkConf.set("es.nodes", "$elasticHost:${elasticPort.toString()}" )
        //sparkConf.set("es.http.retries", "10")
        //sparkConf.set("es.batch.write.retry.count", "10")
        //sparkConf.set("es.batch.write.retry.wait", "30s")
        //sparkConf.set("es.index.auto.create", "true")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate

        //val sesBuild = JavaSparkContext(sparkConf)

//        val columnsAr = arrayOf("id")
        val columnsAr = arrayOf("id","name","description","neighborhood_overview","host_location","host_about",
                "host_neighbourhood",
                "room_type",
                "price",
                "reviews_per_month")

        val superhero = columnsAr.map{ col(it) }.toTypedArray()

        val rdd: JavaRDD<Map<String, String>> = sesBuild.read()
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
                .withColumnRenamed("neighborhood_overview", "neighborhoodOverview")
                .withColumnRenamed("host_location", "location")
                .withColumnRenamed("host_about","about")
                .withColumnRenamed("room_type","type")
                .withColumnRenamed("reviews_per_month","reviewsPerMonth")
                .withColumnRenamed("host_neighbourhood","neighbourhood")
//                .withColumnRenamed("id","_id")
                .toJavaRDD()
                .map { row ->
                    (columnsAr.indices)
                            .map { index -> columnsAr[index] to row.getString(index) }
                            .associateBy({ it.first }, { it.second })
                            .toMap()
                }
                .filter { row -> (row["id"]?.length ?: 0) < 50 }

//        println(rdd.take(10))

        JavaEsSpark.saveToEs(rdd, index, Collections.singletonMap("es.mapping.id", "id"))
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


        val rdd: JavaRDD<Map<String,*>>  = sesBuild
                .read()
                .format("com.databricks.spark.xml")
                .option("rowTag","row")
                .load(path)
                .toJavaRDD()
                .map{row-> mapOf("name" to row.getAs<String>("_DisplayName"),
                        "creationDate" to row.getAs<Date>("_CreationDate"),
                        "_id" to UUID.randomUUID().toString())
                }

        JavaEsSpark.saveToEs(rdd, index, Collections.singletonMap("es.mapping.id", "_id"))
        sesBuild.stop()

    }

}


fun main() {

    val file = File("D:/GIT/distributed-applications/airbnb-root/sparkTransferElastic/src/main/resources/application.properties")

    val prop = Properties()
    FileInputStream(file).use { prop.load(it) }

    SparkTransferElastic.loadFromCsvElastic(prop.getProperty("dump.rooms.path"),
            prop.getProperty("elastic.port"),
            prop.getProperty("elastic.host"),
            prop.getProperty("elastic.flows").toInt(),
            prop.getProperty("elastic.rooms.index")
    )

            /*SparkTransferElastic.loadFromXmlElastic(prop.getProperty("dump.clients.path"),
            prop.getProperty("elastic.port"),
            prop.getProperty("elastic.host"),
            prop.getProperty("elastic.flows").toInt(),
            prop.getProperty("elastic.clients.index"))*/

}