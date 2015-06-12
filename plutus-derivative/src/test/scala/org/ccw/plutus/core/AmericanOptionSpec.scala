package org.ccw.plutus.core

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.derivative.option.model.AmericanCallOption
import org.ccw.plutus.core.derivative.option.model.AmericanPutOption
import org.ccw.plutus.core.model.equities.Stock
import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.derivative.option.pricing.BinomialOptionModel
import org.joda.time.LocalDate
import org.scalatest.BeforeAndAfter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.mutable.Queue

object AmericanOptionSpec

@RunWith(classOf[JUnitRunner])
class AmericanOptionSpec extends FlatSpec with BeforeAndAfter {

  val logger: Logger = LoggerFactory.getLogger(AmericanOptionSpec.getClass())

  var stock: Stock = _
  var expiryDate: LocalDate = _
  var settlementDate: LocalDate = _
  var americanCall: AmericanCallOption = _
  var americanPut: AmericanPutOption = _
  var spotPrice: BigDecimal = _
  var callStrikePrice: BigDecimal = _
  var putStrikePrice: BigDecimal = _

  before {
    stock = new Stock(1, "0005", "HSBC", "HKEX")
    expiryDate = new LocalDate(2014, 1, 31)
    settlementDate = new LocalDate(2014, 1, 31)
    spotPrice = BigDecimal("45")
    callStrikePrice = BigDecimal("45")
    putStrikePrice = BigDecimal("45")
    americanCall = new AmericanCallOption(stock, BigDecimal("45"),
      settlementDate, expiryDate)
    americanPut = new AmericanPutOption(stock, BigDecimal("45"),
      settlementDate, expiryDate)
  }

  "recurvie call " should " work " in {
    val initQ = Queue[Double]()
    initQ.enqueue(5)
    val n = 4
    val q = BinomialOptionModel.generatePriceTree(initQ, List[Double](), 2, 0.5, 1, n, true)
    BinomialOptionModel.getPriceTreeAtNode(q, 2) foreach { x => logger.debug(s"$x") }
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

  "test" should " be OK " in {
    logger.debug("This is i from 0 to 3 ")
    for (i <- 0 to 3) {
      logger.debug(s"$i")
    }
    logger.debug("This is i from 0 until 3 ")
    for (i <- 0 until 3) {
      logger.debug(s"$i")
    }
    logger.debug("This is i from 0 until 0 ")
    for (i <- 0 until 0) {
      logger.debug(s"$i")
    }
    logger.debug("***End of This is i from 0 until 0 ***")

    logger.debug("This is i from 0 to 0 ")
    for (i <- 0 to 0) {
      logger.debug(s"$i")
    }
    logger.debug("***End of This is i from 0 to 0 ***")
  }

  
  "An American Call () " should " be able to calc option price without dividend payment " in {

    val stockPrice = BigDecimal("45")
    val volatility = BigDecimal("0.2")
    val r = BigDecimal("0.06")

    val today = expiryDate.minusDays(30)

    val optionPrice = BinomialOptionModel.getOptionPrice(americanCall,
      today, stockPrice, r, volatility)

    // it should be non-negative

    logger.debug(s"option Price = $optionPrice")
    assert(optionPrice >= BigDecimal("0"))

  }

  "An American Call" should " be able to calc option price without dividend payment " in {

    val stockPrice: Double = 45
    val volatility: Double = 0.2
    val r: Double = 0.06

    val today = expiryDate.minusDays(180)

    val priceList = for {i <- 10 to 20 
       steps = i * 10
       callOptionPrice = BinomialOptionModel.getCallPrice(americanCall,
        today, stockPrice, r, volatility, steps)

      //logger.debug(s"Call option Price = $callOptionPrice using $steps")
      // it should be always non-negative
     
    } yield callOptionPrice
    val callPrice = (priceList.sum / priceList.size)
    logger.debug(s"Call option Price = $callPrice")
    
  }

  "An American Put " should " be able to calc price without dividend payment " in {
    val stockPrice: Double = 45
    val volatility: Double = 0.2
    val r: Double = 0.06

    val today = expiryDate.minusDays(180)

     val priceList = for {i <- 10 to 20
      steps = i * 10
      putOptionPrice = BinomialOptionModel.getPutPrice(americanPut,
        today, stockPrice, r, volatility, steps)

      //logger.debug(s"Put option Price = $putOptionPrice using $steps")
      // it should be always non-negative
      //assert(putOptionPrice >= BigDecimal("0"))
    } yield putOptionPrice
    val putPrice = (priceList.sum / priceList.size)
    logger.debug(s"Put option Price = $putPrice")
  }

}