package org.ccw.plutus.core.test.db

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.db.adapter.HikariAdapter
import org.ccw.plutus.core.db.adapter.BonecpAdapter
import org.ccw.plutus.core.db.DBUtil

import slick.dbio.DBIO
import slick.driver.H2Driver.api._
import org.ccw.plutus.core.model.equities._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@RunWith(classOf[JUnitRunner])
class DividendSpec extends FlatSpec {

   "Dividend Table " should " be auto-genertaed" in {
     
     val s = Dividends.schema
     s.createStatements foreach println
     
     val f = Dividends.findByName
     val c = f("0005.HK")
     val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")
     
     val a = new java.sql.Date(System.currentTimeMillis())
     val setup = DBIO.seq(s.create,
     Dividends ++= Seq(
         (1, "0005.HK", a, a, a, new java.math.BigDecimal("0.1"))
     )
     )
     db.run(setup)
     
     val future  = db.run(c.result)
     future.onSuccess {case x => println(x)}
     
   }
}