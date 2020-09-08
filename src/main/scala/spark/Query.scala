/**
 * Author              : Marina Olina
 * Author email        : marina.olina@inox.lv
 * Object Name         : Query
 * Script Creation Date: 05.09.2020
 * Description         : Designed to query data from parquet persisted locally
 */


package spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger.ProcessingTime
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructType}


object Query {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.INFO)

    val spark = SparkSession
      .builder
      .config("spark.master", "local[2]")
      .appName("BigDataQuery")
      .getOrCreate()

//    spark.sql("select deviceId from parquet.`persist` limit 5").write.parquet("hdfs://localhost:9000/user/root/persist")

    spark.sql("select count(*) from parquet.`persist`").show(truncate = false)


    println("1.\tThe maximum temperatures measured for every device.")

    spark.sql("select deviceId, max(temperature) from parquet.`persist` group by deviceId")
      .show(truncate = false)

    println("2.\tThe amount of data points aggregated for every device.")

    spark.sql("select deviceId, count(*) as datapoints_amount from parquet.`persist` group by deviceId")
      .show(truncate = false)

    println("3.\tThe highest temperature measured on a given day for every device.")

    spark.sql(s"select deviceId, max(temperature), date_format(time, 'dd.MM.yyyy') as datapoint_day" +
      s" from parquet.`persist` where date_format(time, 'yyy-MM-dd')=(current_date) group by datapoint_day, deviceId")
      .show(truncate = false)
  }
}

