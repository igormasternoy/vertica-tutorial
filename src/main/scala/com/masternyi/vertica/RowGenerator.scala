package com.masternyi.vertica

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import scala.util.Random

case class VerticaRow(ts: Timestamp, date: Date, game: String, clicks: Int, impressions: Int)

object RowGenerator {
  val Hour = 60 * 60 * 1000
  val Minute = 60 * 1000
  val rnd = new Random()
  val gameNames = Array("astrokings", "terragenis", "slotomania", "angry_birds")
  val dateFmt = new SimpleDateFormat("yyyy-MM-dd")

  def nextRow(dateStr: String): VerticaRow = {
    val date = dateFmt.parse(dateStr)
    val ts = new Timestamp(date.getTime + Hour * rnd.nextInt(23) + Minute * rnd.nextInt(59))
    VerticaRow(ts, new java.sql.Date(date.getTime), gameNames(rnd.nextInt(gameNames.size - 1)), rnd.nextInt(100), rnd.nextInt(1000))
  }


}
