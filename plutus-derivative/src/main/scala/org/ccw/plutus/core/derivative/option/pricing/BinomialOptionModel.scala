package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate
import scala.collection.mutable.Queue
import org.joda.time.Days

object BinomialOptionModel extends OptionPricingModel {

  val TRADING_DAYS_IN_YEAR: Double = 250

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
    val offset = (depth * (depth - 1)) / 2
    q.drop(offset).take(depth)
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
                     currentDate: LocalDate, spotPrice: BigDecimal, r: BigDecimal,
                     volatility: BigDecimal): BigDecimal = {

    if (currentDate.isBefore(option.expiryDate)) {
      // not yet expired, calculate the price

      // day count == depth of the tree
      val depth = Days.daysBetween(currentDate, option.expiryDate).getDays
      val t = 1 / TRADING_DAYS_IN_YEAR
      val u = Math.exp(Math.sqrt(t.doubleValue()) * volatility.toDouble)
      val d = 1 / u

      val p = ((Math.exp((t * r).toDouble)) - d) / (u - d)

      println(s" u = $u, p = $p, d = $d, dayCount = $depth, t = $t, spot = $spotPrice")

      val initQ = Queue[Double]()
      initQ.enqueue(spotPrice.toDouble)

      val priceTree = generatePriceTree(initQ, List[Double](), u, d, 1, depth, true)

      val callValueTree = new Array[BigDecimal](depth)

      val stockPriceListAtTerminal = getPriceTreeAtNode(priceTree, depth)

      println("*******STOCK PRICE AT TERMINAL*******")
      stockPriceListAtTerminal foreach println
      println("*******STOCK PRICE AT TERMINAL*******")

      // build the terminal call value
      for (i <- 0 to depth - 1) {
        val exerciseValue = stockPriceListAtTerminal(i) - option.strikePrice
        if (exerciseValue > 0) {
          callValueTree(i) = exerciseValue
        } else {
          callValueTree(i) = 0
        }
      }
      callValueTree foreach println
      println("***end of teminal call value print")
      
      for (n <- depth - 1 to 1 by -1) {
        // start with terminal nodes first 
        for (i <- 0 to n - 1) {
          val stockPriceList = getPriceTreeAtNode(priceTree, n)
          stockPriceList foreach println
          println("***end of stock price print")
          val exerciseValue = option.strikePrice - stockPriceListAtTerminal(i)
          val callValue = (p * callValueTree(i) + (1 - p) * callValueTree(i + 1)) * Math.exp(r.toDouble * t * -1)
          if (callValue > exerciseValue) {
            callValueTree(i) = callValue
          } else {
            callValueTree(i) = exerciseValue
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