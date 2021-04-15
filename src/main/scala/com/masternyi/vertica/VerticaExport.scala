package com.masternyi.vertica

import org.apache.spark.sql.SparkSession

object VerticaExport {

  val spark: SparkSession = SparkSession
    .builder()
    .appName("vertica-spark-export")
    .master("local[1]")
    .getOrCreate()

  def main(args: Array[String]): Unit = {

    import Constants._
    val opt = Map("host" -> Host,
      "table" -> Table,
      "db" -> Db,
      "numPartitions" -> Part,
      "user" -> User,
      "password" -> Password)

    val df = spark.read
      .format("com.vertica.spark.datasource.DefaultSource")
      .options(opt)
      .load()

    df.show(false)

    df.write
      .mode("overwrite")
      .saveAsTable(TargetHiveTable)

    spark.stop()
  }

}
