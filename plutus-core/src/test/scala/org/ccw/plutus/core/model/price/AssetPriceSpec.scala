package org.ccw.plutus.core.model.price

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import slick.driver.H2Driver.api._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.Assertions._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.time.{ Millis, Seconds, Span }

@RunWith(classOf[JUnitRunner])
class PriceSpec extends FlatSpec with ScalaFutures {

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))

  val s = AssetPrices.schema

  val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

  val setupTable = DBIO.seq(s.create)

  db.run(setupTable)

  "Price Table " should " be auto-genertaed" in {
    val a = new java.sql.Date(System.currentTimeMillis())
    val price = new java.math.BigDecimal("0.1")
    val dataSetUp = DBIO.seq(
      //s.create,
      AssetPrices ++= Seq(
        ("0005.HK", a, price, price, price, price),
        ("0005.HK", a, price, price, price, price) //("0005.HK", a, price, price, price, price)
        ))
    val futuure = db.run(dataSetUp)
    //setupFutuure.onSuccess { case x => println("success for table creation and data injection")}
    //setupFutuure.onFailure { case f => println("failures")}
    whenReady(futuure.failed) {
      result => println("##########I am good ar " + result.toString)
    }

    Thread.sleep(1000)

  }

  "Query SQL " should " be able to look up data by insturment " in {
    val f = AssetPrices.findByInstrumentCode
    val c = f("0015.HK")
    val now = new java.sql.Date(System.currentTimeMillis())
    val price = new java.math.BigDecimal("0.1")

    val future = db.run(c.result)
    whenReady(future) {
      result =>
        {
          result.size == 0
        }
    }

    val dataSetUp = DBIO.seq(
      AssetPrices ++= Seq(
        ("0015.HK", now, price, price, price, price)))

    whenReady(db.run(dataSetUp)) {
      x =>
        {
          val future2 = db.run(c.result)
          whenReady(future2) {
            result =>
              {
                assert(result.size == 1)
              }
          }
        }
    }

  }

  "Price Data " should "be able to persist from Model to DB" in {
    val now = new java.sql.Date(System.currentTimeMillis())
    val price = new java.math.BigDecimal("0.2")
    val add = AssetPrices += ("0009.HK", now, price, price, price, price)

    val future = db.run(add)

    val symbolFilter = AssetPrices.filter(_.instrumentCode === "0009.HK")

    val q = for {
      c <- AssetPrices if c.instrumentCode === "0009.HK"
    } yield (c.highPrice)

    val f = db.run(q.result)
    whenReady(f) {
      x => assert(x.size == 1 && x(0) == 0.2)
    }

    //symbolFilter.update
  }
}