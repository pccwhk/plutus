package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate
import scala.collection.mutable.Queue

object BinomialOptionModel extends OptionPricingModel {

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

  def getOptionPrice(option: VanillaOption,
                     currentDate: LocalDate, spotPrice: BigDecimal,
                     volatility: BigDecimal): BigDecimal = {

    if (currentDate.isBefore(option.expiryDate)) {
      // not yet expired

      BigDecimal("-1")
    } else if (currentDate.isEqual(option.expiryDate)) {
      option.payOffOnExpiry(spotPrice)
    } else {
      // expired 
      BigDecimal("0")
    }

  }

}