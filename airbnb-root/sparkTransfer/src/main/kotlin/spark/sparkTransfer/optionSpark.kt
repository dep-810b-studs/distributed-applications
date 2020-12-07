package spark.sparkTransfer

import com.mongodb.spark.MongoConnector
import org.apache.spark.api.java.JavaSparkContext
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.WriteConfig
import org.apache.commons.codec.binary.StringUtils
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*
import org.apache.spark.sql.catalog.Column
import org.apache.spark.sql.functions.col
import ru.mai.dep810.airbnb.server.data.Room
import java.io.StringReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.lang.StringUtils.isNotBlank
import java.util.*
import javax.swing.text.Document


interface ISparkTransfer{
    fun loadFromCsv(path: String)
    fun loadFromXml(path:String)
}

object SparkTransfer : ISparkTransfer {


    val columnsAr = arrayOf("name","description","neighborhood_overview","host_location","host_about",
            "host_neighbourhood",
            "room_type",
            "price",
            "reviews_per_month")

    override fun loadFromXml(path: String) {

        val sparkConf: SparkConf = SparkConf().setAppName("Spark Tags Popularity")
        sparkConf.setMaster("local[4]")
        sparkConf.set("spark.mongodb.output.uri","mongodb://localhost:27018/AirBnB.Clients")
        sparkConf.set("spark.mongodb.output.database","AirBnB")
        sparkConf.set("spark.mongodb.output.collection","Clients")
        sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate

        val df:JavaRDD<Document> = sesBuild.read()
                .format("xml")
                //.option("escape", null)
                .load(path)
                .rdd()
                //.map(XmlUtils::parseXmlToMap)
                .filter{m -> StringUtils.isNotBlank(m.get(1))}
                //.map{XmlUtils::unescape}
                .map{d -> Document(Collections.unmodifiableMap(d))}


        MongoSpark.save(df);



    }



    override fun loadFromCsv(path: String) {


        val sparkConf: SparkConf = SparkConf().setAppName("Spark Tags Popularity")
            sparkConf.setMaster("local[4]")
            sparkConf.set("spark.mongodb.output.uri","mongodb://localhost:27018/AirBnB.Rooms")
            sparkConf.set("spark.mongodb.output.database","AirBnB")
            sparkConf.set("spark.mongodb.output.collection","Rooms")
            sparkConf.set("spark.mongodb.output.maxBatchSize","1024")

        val sesBuild = SparkSession.builder().config(sparkConf).orCreate

        val df:Dataset<Row> = sesBuild.read()
                .format("csv")
                .option("sep","=")
                .option("header","true")
                .option("quote", "")
                .option("escape",null)
                .option("inferSchema", "true")
                //.option("escape", null)
                .load(path)
                //.select(*(columnsAr.map { col(it) }.toTypedArray()))


        //System.out.println(df.head(3))
        //MongoSpark.save(df)
        val temp = df.head(1)
        System.out.println(temp)
        val reader = CSVParser((StringReader(temp as String)), CSVFormat.DEFAULT.withHeader())
        System.out.println(reader)






    }
}


fun main(args: Array<String>) {
    SparkTransfer.loadFromCsv("airbnb-root/server/src/main/resources/listings.csv")
    //SparkTransfer.loadFromXml("airbnb-root/server/src/main/resources/Users.xml")
}