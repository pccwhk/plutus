package org.ccw.plutus.core.derivative.futures

import org.joda.time.LocalDate

trait InterestRateFuture {

  val rateInterval = 0.25

  def impliedProbabilityFromFuturesPrice(futurePrice: Double, currentRate: Double, expiryDate: LocalDate) = {
    val interestRateImplied = 100 - futurePrice

    for (i <- 1 to 10) {
      val rateRise = i * rateInterval
      val differential = interestRateImplied - currentRate
      if (differential < 0) {

      } else if (differential > 0) {
          println(s"probability for $rateRise = " + differential / rateRise )
      } else {
        // no change 
      }
    }

  }

}