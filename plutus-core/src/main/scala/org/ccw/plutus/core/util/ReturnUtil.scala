package org.ccw.plutus.core.util

object ReturnUtil {

  final val emptyList = List[BigDecimal]()

  def getReturn(initialValue: BigDecimal, finalValue: BigDecimal) = {
    (finalValue / initialValue) - 1
  }

  def getCumulativeReturn(prices: List[BigDecimal]): List[BigDecimal] = {
    if (prices.size <= 1) emptyList
    else {
      cumulativeReturn(prices(0), prices.drop(1), emptyList)
    }
  }

  private def cumulativeReturn(first: BigDecimal, prices: List[BigDecimal], result: List[BigDecimal]): List[BigDecimal] = {
    if (prices.isEmpty) result.reverse
    else {
      prices match {
        case n :: tail => {
          cumulativeReturn(n, tail, (n / first) :: result)
        }
        case _ => {
          result.reverse
        }
      }
    }
  }
}