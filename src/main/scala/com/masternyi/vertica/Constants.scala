package com.masternyi.vertica

object Constants {
  //Connection
  val Host = "localhost"
  val Port = "5433"
  val Db = "docker"
  val Table = "export_test"
  val User = "dbadmin"
  val Password = ""
  val Part = "1";

  val ConnectionUrl = s"jdbc:vertica://$Host:$Port/$Db"
  //Props
  val TargetHiveTable = "target_hive_table"

}
