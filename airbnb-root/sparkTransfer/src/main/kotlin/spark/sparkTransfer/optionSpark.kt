package spark.sparkTransfer

import com.mongodb.spark.MongoConnector
import org.apache.spark.api.java.JavaSparkContext
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.WriteConfig
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*
import org.apache.spark.sql.catalog.Column
import org.apache.spark.sql.functions.col



interface ISparkTransfer{
    fun loadFromCsv(path: String)
}

object SparkTransfer : ISparkTransfer {


    val columnsToUse = arrayOf("ab", "ba")

    override fun loadFromCsv(path: String) {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark Tags Popularity")
            sparkConf.setMaster("local[4]")
            sparkConf.set("spark.mongodb.output.uri","mongodb://localhost:27018/AirBnB.Rooms")
            sparkConf.set("spark.mongodb.output.database","AirBnB")
            sparkConf.set("spark.mongodb.output.collection","Rooms")
            sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate

        val df: Dataset<Row> = sesBuild.read()
                .format("csv")
                .option("header", "true")
                .option("delimiter", ",")
                .load(path)
                .select(col("name"),
                        col("description"),
                        col("neighborhood_overview"),
                        col("host_location"),
                        col("host_about"),
                        col("host_neighbourhood"),
                        col("room_type"),
                        col("price"),
                        col("reviews_per_month"))



        System.out.println(df.count())
        MongoSpark.save(df)





    }
}


fun main(args: Array<String>) {
    val a = SparkTransfer.loadFromCsv("airbnb-root/server/src/main/resources/listings.csv")
}