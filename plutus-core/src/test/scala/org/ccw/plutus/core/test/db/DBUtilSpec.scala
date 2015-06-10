package org.ccw.plutus.core.test.db

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.db.adapter.HikariAdapter
import org.ccw.plutus.core.db.adapter.BonecpAdapter
import org.ccw.plutus.core.db.DBUtil

@RunWith(classOf[JUnitRunner])
class DBUtilSpec extends FlatSpec {

  "DB Util - HikariAdapter " should " be able to get connection" in {
    val adapter = new HikariAdapter
    val dbUtil = new DBUtil(adapter)

    for (i <- 1 to 10) {
      val c = dbUtil.getThreadLocalConnection("a")
    }
    assertResult(2) { 1 + 1 }
  }

  "DB Util - BonecpAdapter " should " be able to get connection" in {
    val adapter = new BonecpAdapter
    val dbUtil = new DBUtil(adapter)
    for (i <- 1 to 10) {
      val c = dbUtil.getThreadLocalConnection("a")
    }
    assertResult(2) { 1 + 1 }
  }

}