package org.ccw.plutus.core

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.derivative.option.model.AmericanCallOption
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.derivative.option.pricing.BinomialOptionModel
import org.joda.time.LocalDate

@RunWith(classOf[JUnitRunner])
class AmericanOptionSpec extends FlatSpec {

  "An Amercian Call " should " be early exercisable " in {
    val stock = new Stock(1, "0005", "HSBC", "HKEX")
    val expiryDate = new LocalDate(2014, 1, 10)
    val settlementDate = new LocalDate(2014, 1, 12)
    val americanCall = new AmericanCallOption(stock, BigDecimal("80"),
      settlementDate, expiryDate)

    val now = expiryDate.minusDays(2)
    
    assertResult(true) {
      americanCall.isEarlyExercisable
    }
    
    assertResult(true) {
      americanCall.isExercisableNow(now)
    }
    
  }

  "An American Call" should " be able to calc price with dividend payment " in {
    val stock = new Stock(1, "0005", "HSBC", "HKEX")
    val expiryDate = new LocalDate(2014, 1, 10)
    val settlementDate = new LocalDate(2014, 1, 12)
    val americanCall = new AmericanCallOption(stock, BigDecimal("80"),
      settlementDate, expiryDate)
    val stockPrice = BigDecimal("85")
    val optionPrice = BigDecimal("5")
    
    val today = expiryDate.minusDays(2)
    
    BinomialOptionModel.getImpliedVolatility(americanCall, today,
      optionPrice, stockPrice);

  }

}