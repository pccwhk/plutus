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
    expiryDate = new LocalDate(2014, 1, 31)
    settlementDate = new LocalDate(2014, 1, 31)
    americanCall = new AmericanCallOption(stock, BigDecimal("25"),
      settlementDate, expiryDate)
  }

  "recurvie call " should " work " in {
    val initQ = Queue[Double]()
    initQ.enqueue(5)
    val n = 4
    val q = BinomialOptionModel.generatePriceTree(initQ, List[Double](), 2, 0.5, 1, n, true)
    //BinomialOptionModel.getPriceTreeAtNode(q, 3) foreach println
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

    val stockPrice = BigDecimal("25")
    val volatility = BigDecimal("0.2")
    val r = BigDecimal("0.001")

    val today = expiryDate.minusDays(2)

    val optionPrice = BinomialOptionModel.getOptionPrice(americanCall,
      today, stockPrice, r, volatility)

    // it should be non-negative

    println(s"option Price = $optionPrice")
    assert(optionPrice >= BigDecimal("0"))

  }

  "An American Call" should " be able to calc price with dividend payment " in {

  }

}