package org.ccw.plutus.core.model.asset

import slick.driver.H2Driver.api._
import slick.lifted.{ ProvenShape, ForeignKeyQuery }

object AssetReturns extends TableQuery(new AssetReturn(_)) {
  val findByInstrumentCode = this.findBy(_.instrumentCode)
}

class AssetReturn(tag: Tag) extends Table[(Int, String, java.sql.Date, BigDecimal)](tag, "asset_return") {

  def id = column[Int]("ID", O.PrimaryKey)
  def instrumentCode = column[String]("INSTR_CODE")
  def date = column[java.sql.Date]("DATE")
  def dailyReturn = column[BigDecimal]("DAILY_RETURN")

  def * : ProvenShape[(Int, String, java.sql.Date, BigDecimal)] =
    (id, instrumentCode, date, dailyReturn)
}