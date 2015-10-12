package org.ccw.plutus.core.util

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

@RunWith(classOf[JUnitRunner])
class UtilSpec extends FlatSpec {

  "Date Util " should " count the days correctly for one day" in {

    val localDate1 = LocalDate.of(2005, 3, 26);
    val localDate2 = LocalDate.of(2005, 3, 27);
    assertResult(1) {
      Period.between(localDate1, localDate2).getDays
    }

  }

  "Date Util " should " count the days correctly for 4 days" in {

    val localDate1 = LocalDate.of(2005, 3, 26);
    val localDate2 = LocalDate.of(2005, 3, 30);
    assertResult(4) {
      Period.between(localDate1, localDate2).getDays
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
    } else {
      println((q) * 10 + i)
    }
  }

}