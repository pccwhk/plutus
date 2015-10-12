package org.ccw.plutus.core.model.asset

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import slick.driver.H2Driver.api._
import org.ccw.plutus.core.model.equities._

import scala.concurrent.ExecutionContext.Implicits.global

@RunWith(classOf[JUnitRunner])
class ReturnSpec extends FlatSpec {

  "Return Table " should " be auto-genertaed" in {

    val s = AssetReturns.schema
    s.createStatements foreach println

    val f = AssetReturns.findByInstrumentCode
    val c = f("0005.HK")
    val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    val a = new java.sql.Date(System.currentTimeMillis())
    val setup = DBIO.seq(s.create,
      AssetReturns ++= Seq(
        (1, "0005.HK", a, new java.math.BigDecimal("0.1")),
        (2, "0005.HK", a, new java.math.BigDecimal("0.2")),
        (3, "0005.HK", a, new java.math.BigDecimal("0.3"))))
    db.run(setup)

    val future = db.run(c.result)
    future.onSuccess { case x => x foreach (println) }
    future.onComplete { case y => println("i am done") }

    Thread.sleep(100)

  }
}