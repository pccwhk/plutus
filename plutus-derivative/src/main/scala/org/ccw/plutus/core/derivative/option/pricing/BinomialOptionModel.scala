package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate
import scala.collection.mutable.Queue
import org.joda.time.Days

object BinomialOptionModel extends OptionPricingModel {
  
  val TRADING_DAYS_IN_YEAR: BigDecimal = 250

  def getImpliedVolatility(option: VanillaOption,
                           currentDate: LocalDate, optionPrice: BigDecimal, underlyingPrice: BigDecimal): BigDecimal = {
    throw new Exception();
  }

  def helper(result: Queue[Double], nextList: List[Double],
             u: Double, d: Double, i: Integer, n: Integer, isFirst: Boolean): Queue[Double] = {
    if (i > n) {
      result
    } else {
      nextList match {
        case head :: tail => {
          if (isFirst) {
            result.enqueue(head * u)
            result.enqueue(head * d)
            helper(result, tail, u, d, i, n, false)
          } else {
            result.enqueue(head * d)
            helper(result, tail, u, d, i, n, false)
          }
        }
        case _ => {
          helper(result, result.takeRight(i).toList, u, d, i + 1, n, true)
        }
      }

    }
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
      val u = BigDecimal(Math.exp(Math.sqrt(t.doubleValue()) * volatility.toDouble))
      val d = 1 / u

      val p = ((Math.exp((t * r).toDouble)) - d) / (u - d)

      println(s" u = $u, p = $p, d = $d, dayCount = $depth, t = $t")

      val priceData = new Array[BigDecimal](depth)

      for (i <- 0 until depth) {
        priceData(i) = spotPrice * u.pow(depth - (2 * i))
      }

      for (n <- depth to 0) {
        for (i <- 0 to n - 1) {
          val call = p * priceData(i) + (1 - p) * priceData(i + 1)
          //val exercise = option.strikePrice -  
        }
      }

      priceData(0)
    } else if (currentDate.isEqual(option.expiryDate)) {

      option.payOffOnExpiry(spotPrice)
    } else {
      // expired 
      BigDecimal("0")
    }

  }

}