package spark.sparkTransfer

import com.mongodb.spark.MongoSpark
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.functions.callUDF
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.types.DataTypes
import org.bson.Document
import java.util.*

//import java.util.*


interface ISparkTransfer{
    fun loadFromCsv(path: String, database: String,host: String,port: Int,flows: Int)
    fun loadFromXml(path: String, database:String, host:String, port:Int, flows:Int)
}

object SparkTransfer : ISparkTransfer {


    override fun loadFromXml(path: String, database:String, host:String, port:Int, flows:Int) {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark XML loader")
        sparkConf.setMaster("local[$flows]")
        sparkConf.set("spark.mongodb.output.uri","mongodb://$host:$port/$database.Clients")
        sparkConf.set("spark.mongodb.output.database","$database")
        sparkConf.set("spark.mongodb.output.collection","Clients")
        sparkConf.set("spark.mongodb.output.maxBatchSize","1024")
        //sparkConf.set("spark.mongodb.output.shardKey", "id")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate


        val df  = sesBuild
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


        MongoSpark.save(df)
        sesBuild.stop()



    }



    override fun loadFromCsv(path: String, database: String, host: String, port: Int, flows: Int)  {


        val sparkConf: SparkConf = SparkConf().setAppName("Spark CSV loader")
            sparkConf.setMaster("local[$flows]")
            sparkConf.set("spark.mongodb.output.uri","mongodb://$host:$port/$database.Rooms")
            sparkConf.set("spark.mongodb.output.database","$database")
            sparkConf.set("spark.mongodb.output.collection","Rooms")
            sparkConf.set("spark.mongodb.output.maxBatchSize","1024")
            //sparkConf.set("spark.mongodb.output.shardKey", "id")


        val sesBuild = SparkSession.builder()
                .config(sparkConf)
                .orCreate

        //val func_prep:String = UUID.randomUUID().toString()

        //val udfRun = udf({UUID.randomUUID().toString()},)


        //sesBuild.udf().register( "randUdf", udf({_-> UUID.randomUUID().toString()}, DataTypes.StringType).asNondeterministic())

        //val tr = udf({UUID.randomUUID().toString()},DataTypes.StringType)

        val columnsAr = arrayOf("id","name","description","neighborhood_overview","host_location","host_about",
                "host_neighbourhood",
                "room_type",
                "price",
                "reviews_per_month")

        val superhero = columnsAr.map{ col(it) }.toTypedArray()

        //val superhero = columnsAr.map{ it }

        //System.out.println(superhero)

        val df = sesBuild.read()
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


/*fun main(args: Array<String>) {
    SparkTransfer.loadFromXml("D:/GIT/distributed-applications/airbnb-root/server/src/main/resources/us.xml",
            "AirBnb","localhost",27018,5)
    SparkTransfer.loadFromCsv("D:/GIT/distributed-applications/airbnb-root/server/src/main/resources/listings.csv",
    "AirBnb","localhost",27018,5)

}*/