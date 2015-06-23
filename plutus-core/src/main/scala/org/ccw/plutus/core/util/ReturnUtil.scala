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

  def getTotalReturn(prices: List[BigDecimal]) :BigDecimal= {
    // we can use reduce here because multiplication is distributive and associative
    getCumulativeReturn(prices).reduce(_ * _)
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