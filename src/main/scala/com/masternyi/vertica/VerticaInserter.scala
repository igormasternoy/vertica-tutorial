package com.masternyi.vertica

import java.sql.{Connection, Date, DriverManager, PreparedStatement, SQLException, SQLInvalidAuthorizationSpecException, SQLTransientConnectionException, Timestamp}
import java.util.Properties
import java.time.LocalDateTime

object VerticaInserter {

  val SqlInsert = "INSERT INTO export_test (event_ts, event_date, game_id_str, clicks, impressions) " +
    "VALUES " +
    "(?, ?, ?, ?, ?);"

  def main(args: Array[String]): Unit = {
    val myProp = new Properties()
    myProp.put("user", "dbadmin")
    myProp.put("password", "")

    var conn: Connection = null
    try {
      conn = DriverManager.getConnection(Constants.ConnectionUrl, myProp)

      val rows = generateRows(Integer.valueOf(args(0)), args(1))
      rows.foreach(row => {
        prepareInsert(row, conn).executeUpdate()
        System.out.println("Inserting row")
      })

    } catch {
      case connException: SQLTransientConnectionException =>
        // There was a potentially temporary network error
        // Could automatically retry a number of times here, but
        // instead just report error and exit.
        System.out.print("Network connection issue: ")
        System.out.print(connException.getMessage)
        System.out.println(" Try again later!")
      case authException: SQLInvalidAuthorizationSpecException =>
        // Either the username or password was wrong
        System.out.print("Could not log into database: ")
        System.out.print(authException.getMessage)
        System.out.println(" Check the login credentials and try again.")
      case e: SQLException =>
        // Catch-all for other exceptions
        e.printStackTrace()
    } finally {
      conn.close()
    }
  }

  def generateRows(amount:Int, date:String):Seq[VerticaRow] ={
    (1 to amount).map(_ => RowGenerator.nextRow(date))
  }

  def prepareInsert(row:VerticaRow, conn: Connection):PreparedStatement = {
    val preparedStatement = conn.prepareStatement(SqlInsert)
    preparedStatement.setTimestamp(1, row.ts)
    preparedStatement.setDate(2, row.date)
    preparedStatement.setString(3, row.game)
    preparedStatement.setInt(4, row.clicks)
    preparedStatement.setInt(5, row.impressions)
    preparedStatement
  }


}
