package org.ccw.plutus.core.model.equities

import java.util.Date

import slick.driver.H2Driver.api._
import slick.lifted.{ ProvenShape, ForeignKeyQuery }

import scala.concurrent.ExecutionContext.Implicits.global

/*
class Dividend2 (
    equity :Stock,
	exdividendDate :Date,
	recordDate :Date,
	paymentDate :Date,
	dividendAmount :BigDecimal) {
	
}*/

object Dividends extends TableQuery(new Dividend(_)) {
  val findByInstrumentCode = this.findBy(_.instrumentCode)
}

class Dividend(tag: Tag) extends Table[(Int, String, java.sql.Date, java.sql.Date, java.sql.Date, BigDecimal)](tag, "dividend") {

  def id = column[Int]("ID", O.PrimaryKey)
  def instrumentCode = column[String]("INSTR_CODE")
  def recordDate = column[java.sql.Date]("RECORD_DATE")
  def exdividendDate = column[java.sql.Date]("EXDIV_DATE")
  def paymentDate = column[java.sql.Date]("PAYMENT_DATE")
  def dividendAmount = column[BigDecimal]("DIV_AMT")

  def * : ProvenShape[(Int, String, java.sql.Date, java.sql.Date, java.sql.Date, BigDecimal)] =
    (id, instrumentCode, recordDate, exdividendDate, paymentDate, dividendAmount)
}

