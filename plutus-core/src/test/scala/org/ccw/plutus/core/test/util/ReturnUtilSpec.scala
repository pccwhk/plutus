package org.ccw.plutus.core.test.util

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.util.Date
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.util.DateUtil
import org.ccw.plutus.core.util.StringUtil
import org.ccw.plutus.core.util.ReturnUtil
import org.joda.time.LocalDate
import org.joda.time.Days
import org.scalatest.Assertions._

@RunWith(classOf[JUnitRunner])
class ReturnUtilSpec extends FlatSpec {
  "ReturnUtil" should "calcuate return for single price" in {
    assertResult(0.2) {
      ReturnUtil.getReturn(1.0, 1.2)
    }
  }

  it should " return empty list for single price" in {
    val prices: List[BigDecimal] = List[BigDecimal](100)
    val r = ReturnUtil.getCumulativeReturn(prices)
    assert(r.isEmpty)
  }

  it should " return empty list for empty price list" in {
    val prices: List[BigDecimal] = List[BigDecimal]()
    val r = ReturnUtil.getCumulativeReturn(prices)
    assert(r.isEmpty)
  }

  it should " return single item list for two prices" in {
    val prices: List[BigDecimal] = List[BigDecimal](100, 120)
    val r = ReturnUtil.getCumulativeReturn(prices)
    assertResult(1.2) {
      r(0)
    }
    assertResult(prices.size - 1) { r.size }
  }

  it should " return bulk item list for multiple  prices" in {
    val prices: List[BigDecimal] = List[BigDecimal](100, 120, 130, 100)
    val r = ReturnUtil.getCumulativeReturn(prices)
    r foreach println
    assertResult(prices.size -1) { r.size }

  }

  it should " return one item list for multiple  prices" in {
    val prices: List[BigDecimal] = List[BigDecimal](100, 100)
    val r = ReturnUtil.getCumulativeReturn(prices)
    assertResult(prices.size - 1) { r.size }
    assertResult(1) { r(0) }
  }
  
  it should " generate correct total return" in {
    val prices: List[BigDecimal] = List[BigDecimal](100, 120, 130, 100)
    val r = ReturnUtil.getTotalReturn(prices)
    assertResult(1.0) { r }
  }

}