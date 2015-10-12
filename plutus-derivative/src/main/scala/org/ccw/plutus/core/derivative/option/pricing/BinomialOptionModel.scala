package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import java.time.LocalDate
import scala.collection.mutable.Queue
import java.time.Period
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object BinomialOptionModel extends OptionPricingModel {

  val logger: Logger = LoggerFactory.getLogger(BinomialOptionModel.getClass())

  val TRADING_DAYS_IN_YEAR: Double = 365

  def getImpliedVolatility(option: VanillaOption,
                           currentDate: LocalDate, optionPrice: BigDecimal, underlyingPrice: BigDecimal): BigDecimal = {
    throw new Exception();
  }

  def generatePriceTree(result: Queue[Double], nextList: List[Double],
                        u: Double, d: Double, i: Integer, n: Integer, isFirst: Boolean): Queue[Double] = {
    if (i > n) {
      result
    } else {
      nextList match {
        case head :: tail => {
          if (isFirst) {
            result.enqueue(head * u)
            result.enqueue(head * d)
            generatePriceTree(result, tail, u, d, i, n, false)
          } else {
            result.enqueue(head * d)
            generatePriceTree(result, tail, u, d, i, n, false)
          }
        }
        case _ => {
          generatePriceTree(result, result.takeRight(i).toList, u, d, i + 1, n, true)
        }
      }

    }
  }

  def getPriceTreeAtNode(q: Queue[Double], depth: Integer) = {
    val d = depth + 1
    val offset = (d * (d - 1)) / 2
    q.drop(offset).take(d)
  }

  def getPutPrice(option: VanillaOption,
                  currentDate: LocalDate, spotPrice: Double, interest: Double,
                  volatility: Double, noOfSteps: Integer = 100): Double = {
    if (currentDate.isBefore(option.expiryDate)) {

      val dayCount = Period.between(currentDate, option.expiryDate).getDays
      val t = (dayCount / noOfSteps.toDouble) / TRADING_DAYS_IN_YEAR
      val u = Math.exp(Math.sqrt(t) * volatility.toDouble)
      val d = 1 / u
      val r = interest

      val probUp = ((Math.exp((t * r))) - d) / (u - d)
      val probDown = 1 - probUp

      //logger.debug(s" u = $u, p = $probUp, d = $d, dayCount = $dayCount, steps  = $noOfSteps, t = $t, spot = $spotPrice")

      val initQ = Queue[Double]()
      initQ.enqueue(spotPrice.toDouble)

      val priceTree = generatePriceTree(initQ, List[Double](), u, d, 1, noOfSteps + 1, true)

      val putValueTree = new Array[Double](noOfSteps + 1)

      val stockPriceListAtTerminal = getPriceTreeAtNode(priceTree, noOfSteps)

      //logger.debug("*******STOCK PRICE AT TERMINAL*******")
      //stockPriceListAtTerminal foreach { x => logger.debug(s"$x") }
      //logger.debug("*******CALL VALUE AT TERMINAL*******")

      // build the terminal call value
      for (i <- 0 to noOfSteps) {
        val exerciseValue = option.strikePrice - stockPriceListAtTerminal(i)
        if (exerciseValue > 0) {
          putValueTree(i) = exerciseValue.toDouble
        } else {
          putValueTree(i) = 0
        }
      }
      //callValueTree foreach { x => logger.debug(s"$x") }

      for (n <- noOfSteps - 1 to 0 by -1) {
        // start with terminal nodes first 
        val stockPriceList = getPriceTreeAtNode(priceTree, n)
        //logger.debug(s"***Stock price print at ${n} stage")
        var i = 0
        stockPriceList foreach { stockPrice =>
          {
            val exerciseValue = option.strikePrice - stockPrice
            val putValue = (probUp * putValueTree(i) + probDown * putValueTree(i + 1)) * Math.exp(r * t * -1)
            //logger.debug(s">>>>> stockPrice at  $stockPrice, Call Value = $callValue, exercise = $exerciseValue at $n stage, node $i")
            if (putValue > exerciseValue) {
              putValueTree(i) = putValue
            } else {
              putValueTree(i) = exerciseValue.toDouble
            }
            i = i + 1
          }
        }
      }
      println(putValueTree(0))
      putValueTree(0)

    } else if (currentDate.isEqual(option.expiryDate)) {
      option.payOffOnExpiry(spotPrice).toDouble
    } else {
      // expired 
      0
    }

  }

  def getCallPrice(option: VanillaOption,
                   currentDate: LocalDate, spotPrice: Double, interest: Double,
                   volatility: Double, noOfSteps: Integer = 100): Double = {
    if (currentDate.isBefore(option.expiryDate)) {

      val dayCount = Period.between(currentDate, option.expiryDate).getDays
      val t = (dayCount / noOfSteps.toDouble) / TRADING_DAYS_IN_YEAR
      val u = Math.exp(Math.sqrt(t) * volatility.toDouble)
      val d = 1 / u
      val r = interest

      val probUp = ((Math.exp((t * r))) - d) / (u - d)
      val probDown = 1 - probUp

      //logger.debug(s" u = $u, p = $probUp, d = $d, dayCount = $dayCount, steps  = $noOfSteps, t = $t, spot = $spotPrice")

      val initQ = Queue[Double]()
      initQ.enqueue(spotPrice.toDouble)

      val priceTree = generatePriceTree(initQ, List[Double](), u, d, 1, noOfSteps + 1, true)

      val callValueTree = new Array[Double](noOfSteps + 1)

      val stockPriceListAtTerminal = getPriceTreeAtNode(priceTree, noOfSteps)

      //logger.debug("*******STOCK PRICE AT TERMINAL*******")
      //stockPriceListAtTerminal foreach { x => logger.debug(s"$x") }
      //logger.debug("*******CALL VALUE AT TERMINAL*******")

      // build the terminal call value
      for (i <- 0 to noOfSteps) {
        val exerciseValue = stockPriceListAtTerminal(i) - option.strikePrice
        if (exerciseValue > 0) {
          callValueTree(i) = exerciseValue.toDouble
        } else {
          callValueTree(i) = 0
        }
      }
      //callValueTree foreach { x => logger.debug(s"$x") }

      for (n <- noOfSteps - 1 to 0 by -1) {
        // start with terminal nodes first 
        val stockPriceList = getPriceTreeAtNode(priceTree, n)
        //logger.debug(s"***Stock price print at ${n} stage")
        var i = 0
        stockPriceList foreach { stockPrice =>
          {
            val exerciseValue = stockPrice - option.strikePrice
            val callValue = (probUp * callValueTree(i) + probDown * callValueTree(i + 1)) * Math.exp(r * t * -1)
            //logger.debug(s">>>>> stockPrice at  $stockPrice, Call Value = $callValue, exercise = $exerciseValue at $n stage, node $i")
            if (callValue > exerciseValue) {
              callValueTree(i) = callValue
            } else {
              callValueTree(i) = exerciseValue.toDouble
            }
            i = i + 1
          }
        }
      }
      println(callValueTree(0))
      callValueTree(0)
    } else if (currentDate.isEqual(option.expiryDate)) {
      option.payOffOnExpiry(spotPrice).toDouble
    } else {
      // expired 
      0
    }
  }

  def getOptionPrice(option: VanillaOption,
                     currentDate: LocalDate, spotPrice: BigDecimal, interest: BigDecimal,
                     volatility: BigDecimal): BigDecimal = {

    if (option.isCall) {
      BigDecimal(getCallPrice(option, currentDate, spotPrice.toDouble, interest.toDouble, volatility.toDouble, 500))
    } else {
      BigDecimal(getPutPrice(option, currentDate, spotPrice.toDouble, interest.toDouble, volatility.toDouble, 500))
    }
  }

}