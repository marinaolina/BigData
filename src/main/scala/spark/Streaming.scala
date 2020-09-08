/**
 * Author              : Marina Olina
 * Author email        : marina.olina@inox.lv
 * Object Name         : Streaming
 * Script Creation Date: 05.09.2020
 * Description         : Designed to read streaming data and persist locally
 */


package spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger.ProcessingTime
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructType}


object Streaming {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)

    val spark = SparkSession
      .builder
      .config("spark.master", "local[2]")
      .appName("BigDataStreaming")
      .getOrCreate()

    import spark.implicits._
    // Create DataFrame representing the stream of input lines from connection to localhost:8080
    val data = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 8080)
      .load()

    println(s"streaming detected: ${data.isStreaming}")

    val schema = new StructType()
      .add("data", new StructType()
        .add("deviceId", StringType)
        .add("temperature", IntegerType)
        .add("location", new StructType()
          .add("latitude", StringType).add("longitude", StringType))
        .add("time", StringType))

    val dataFrame= data
      .select(from_json($"value",schema)
        .alias("tmp"))
      .select("tmp.data.deviceId", "tmp.data.temperature", "tmp.data.location.*", "tmp.data.time")
      .withColumn("latitude", col("latitude").cast(DoubleType))
      .withColumn("longitude", col("longitude").cast(DoubleType))
      .withColumn("time", to_timestamp(from_unixtime(col("time"))))

    dataFrame.printSchema

    // Start running the query to the console
//      val query = dataFrame.writeStream
//        .trigger(ProcessingTime("60 seconds"))
//        .format("console")
//        .option("truncate", value = false)
//        .start()

    //Persisting in parquet

      val querySave = dataFrame
        .withColumn("persisted_batch", current_timestamp())
        .coalesce(1)
        .writeStream
        .partitionBy("deviceId")
        .outputMode("append")
        .trigger(ProcessingTime("60 seconds"))
        .format("parquet")
        .option("checkpointLocation", "checkpoint")
        .option("path", "persist")
        .start()

    querySave.awaitTermination()
//    query.awaitTermination()
  }
}

