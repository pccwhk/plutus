package org.ccw.plutus.core.test.util

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.util.DateUtil
import org.ccw.plutus.core.util.StringUtil
import org.joda.time.LocalDate
import org.joda.time.Days

@RunWith(classOf[JUnitRunner])
class UtilSpec extends FlatSpec {

  "Date Util " should " count the days correctly for one day" in {

    val localDate1 = new LocalDate(2005, 3, 26);
    val localDate2 = new LocalDate(2005, 3, 27);
    assertResult(Days.ONE) {
      Days.daysBetween(localDate1, localDate2)
    }

  }

  "Date Util " should " count the days correctly for 4 days" in {

    val localDate1 = new LocalDate(2005, 3, 26);
    val localDate2 = new LocalDate(2005, 3, 30);
    assertResult(Days.FOUR) {
      Days.daysBetween(localDate1, localDate2)
    }

  }

  "String Util " should " be able to check whether a string is numberic or not" in {
    val number = "123456984"
    assertResult(true) {
      val (r, d) = StringUtil.isNumberic(number)
      r
    }
  }

  "String Util " should " be able to tell a string is not numberic" in {
    val number = "123456984d"
    assertResult(false) {
      val (r, d) = StringUtil.isNumberic(number)
      r
    }
  }
  
  "mask " should " work " in {
    val i = 5
    val q = 2019 / 10 
    val r = 2019 % 10 
    if (r == 9) {
      println((q + 1) * 10 + i)
    }
    else {
      println((q) * 10 + i)
    }
  }

}