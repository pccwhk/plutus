package org.ccw.plutus.core

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.derivative.option.model.AmericanCallOption
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.derivative.option.pricing.BinomialOptionModel
import org.joda.time.LocalDate
import org.scalatest.BeforeAndAfter
import scala.collection.mutable.Queue

@RunWith(classOf[JUnitRunner])
class AmericanOptionSpec extends FlatSpec with BeforeAndAfter {

  var stock: Stock = _
  var expiryDate: LocalDate = _
  var settlementDate: LocalDate = _
  var americanCall: AmericanCallOption = _

  before {
    stock = new Stock(1, "0005", "HSBC", "HKEX")
    expiryDate = new LocalDate(2014, 1, 10)
    settlementDate = new LocalDate(2014, 1, 12)
    americanCall = new AmericanCallOption(stock, BigDecimal("80"),
      settlementDate, expiryDate)
  }

  "recurvie call " should " work " in {
    val initQ = Queue[Double]()
    initQ.enqueue(5)
    val n = 100
    val q = BinomialOptionModel.helper(initQ, List[Double](), 2, 0.5, 1, n, true).toList
    println(q.last)
  }

  "An Amercian Call " should " be early exercisable " in {

    val now = expiryDate.minusDays(2)

    assertResult(true) {
      americanCall.isEarlyExercisable
    }

    assertResult(true) {
      americanCall.isExercisableNow(now)
    }

  }

  "An American Call" should " be able to calc option price without dividend payment " in {

    val stockPrice = BigDecimal("85")
    val volatility = BigDecimal("0.5")

    val today = expiryDate.minusDays(2)

    val optionPrice = BinomialOptionModel.getOptionPrice(americanCall,
      today, stockPrice, volatility)

    // it should be non-negative
    assert(optionPrice >= BigDecimal("0"))

  }

  "An American Call" should " be able to calc price with dividend payment " in {

  }

}