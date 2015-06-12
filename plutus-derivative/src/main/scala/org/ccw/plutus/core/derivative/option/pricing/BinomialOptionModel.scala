package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate
import scala.collection.mutable.Queue
import org.joda.time.Days
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

  def valueAtPeriodN(p: BigDecimal, u: BigDecimal, d: BigDecimal, s: BigDecimal, n: Int,
                     strikePrice: BigDecimal, r: Double, deltaT: Double): BigDecimal = {
    if (n == 0) {
      val callValue = strikePrice - s
      if (callValue > 0) {
        callValue
      } else {
        0
      }

    } else {
      Math.exp(-r * deltaT) * (p * valueAtPeriodN(p, u, d, s * u, n - 1, strikePrice, r, deltaT) + (1 - p) * valueAtPeriodN(p, u, d, s * d, n - 1, strikePrice, r, deltaT))
    }
  }

  def getOptionPrice(option: VanillaOption,
                     currentDate: LocalDate, spotPrice: BigDecimal, interest: BigDecimal,
                     volatility: BigDecimal, daysPerStep: Integer = 1): BigDecimal = {

    if (currentDate.isBefore(option.expiryDate)) {
      // not yet expired, calculate the price

      // day count == depth of the tree
      val dayCount = Days.daysBetween(currentDate, option.expiryDate).getDays
      // t is the number of years per step
      val t = daysPerStep / TRADING_DAYS_IN_YEAR
      val depth = dayCount / daysPerStep
      val u = Math.exp(Math.sqrt(t) * volatility.toDouble)
      val d = 1 / u
      val r = interest.toDouble

      val probUp = ((Math.exp((t * r))) - d) / (u - d)
      val probDown = 1 - probUp

      logger.debug(s" u = $u, p = $probUp, d = $d, dayCount = $dayCount, depth =$depth, t = $t, spot = $spotPrice")

      val initQ = Queue[Double]()
      initQ.enqueue(spotPrice.toDouble)

      val priceTree = generatePriceTree(initQ, List[Double](), u, d, 1, depth + 1, true)

      val callValueTree = new Array[BigDecimal](depth + 1)

      val stockPriceListAtTerminal = getPriceTreeAtNode(priceTree, depth)

      logger.debug("*******STOCK PRICE AT TERMINAL*******")
      stockPriceListAtTerminal foreach { x => logger.debug(s"$x") }
      logger.debug("*******CALL VALUE AT TERMINAL*******")

      // build the terminal call value
      for (i <- 0 to depth) {
        val exerciseValue = stockPriceListAtTerminal(i) - option.strikePrice
        if (exerciseValue > 0) {
          callValueTree(i) = exerciseValue
        } else {
          callValueTree(i) = 0
        }
      }
      callValueTree foreach { x => logger.debug(s"$x") }

      for (n <- depth - 1 to 0 by -1) {
        // start with terminal nodes first 
        val stockPriceList = getPriceTreeAtNode(priceTree, n)
        logger.debug(s"***Stock price print at ${n} stage")
        var i = 0
        stockPriceList foreach { stockPrice =>
          {
            val exerciseValue = stockPrice - option.strikePrice
            val callValue = (probUp * callValueTree(i) + probDown * callValueTree(i + 1)) * Math.exp(r.toDouble * t * -1)
            logger.debug(s">>>>> stockPrice at  $stockPrice, Call Value = $callValue, exercise = $exerciseValue at $n stage, node $i")
            if (callValue > exerciseValue) {
              callValueTree(i) = callValue
            } else {
              callValueTree(i) = exerciseValue
            }
            i = i + 1
          }
        }
      }

      callValueTree(0)
    } else if (currentDate.isEqual(option.expiryDate)) {

      option.payOffOnExpiry(spotPrice)
    } else {
      // expired 
      BigDecimal("0")
    }

  }

}