package org.ccw.plutus.core.model.price

import slick.driver.H2Driver.api._
import slick.lifted.{ ProvenShape, ForeignKeyQuery }

object AssetPrices extends TableQuery(new AssetPrice(_)) {
  val findByInstrumentCode = this.findBy(_.instrumentCode)
}

class AssetPrice(tag: Tag) extends Table[(String, java.sql.Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal)](tag, "asset_price") {

  //def id = column[Int]("ID", O.PrimaryKey)
  def instrumentCode = column[String]("INSTR_CODE")
  def date = column[java.sql.Date]("DATE")
  def openPrice = column[BigDecimal]("OPEN_PRICE")
  def highPrice = column[BigDecimal]("HIGH_RPICE")
  def lowPrice = column[BigDecimal]("LOW_PRICE")
  def closePrice = column[BigDecimal]("CLOSE_PRICE")

  def * : ProvenShape[(String, java.sql.Date, BigDecimal, BigDecimal, BigDecimal, BigDecimal)] =
    (instrumentCode, date, openPrice, highPrice, lowPrice, closePrice)
    
  def pk = primaryKey("pk_1", (instrumentCode, date))
}