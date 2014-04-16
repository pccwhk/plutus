package org.ccw.plutus.core


import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.derivative.option.model.AmericanCallOption
import java.util.Date
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._;
import org.ccw.plutus.core.derivative.option.pricing.BinomialOptionModel

@RunWith(classOf[JUnitRunner])
class AmericanOptionSpec extends FlatSpec {

  "An Amercian Call " should " be early exercisable " in {
	  val stock = new Stock(1, "0005", "HSBC", EQUITY)
	  val americanCall = new AmericanCallOption( stock, BigDecimal("80"), new Date)
	  assertResult(true) {
		  americanCall.isEarlyExercisable
	  }
  }
  
  "An American Call" should " be able to calc price with dividend payment " in {
    val stock = new Stock(1, "0005", "HSBC", EQUITY)
	val americanCall = new AmericanCallOption( stock, BigDecimal("80"), new Date)
    val stockPrice = BigDecimal("85")
    val optionPrice = BigDecimal("5")
    
    BinomialOptionModel.getImpliedVolatility(americanCall, new Date,
        optionPrice, stockPrice);
    
  }
  

}