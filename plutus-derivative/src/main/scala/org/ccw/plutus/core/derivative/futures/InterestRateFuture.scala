package org.ccw.plutus.core.derivative.futures

import java.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object InterestRateFuture {
  
    val logger: Logger = LoggerFactory.getLogger(InterestRateFuture.getClass())

  val rateInterval = 0.25

  def impliedProbabilityFromFuturesPrice(futurePrice: Double, currentRate: Double, expiryDate: LocalDate) = {
    val interestRateImplied = 100 - futurePrice

    for (i <- 1 to 10) {
      val rateRise = i * rateInterval
      val differential = interestRateImplied - currentRate
      if (differential < 0) {
        logger.info("differential is -ve")
      } else if (differential > 0) {
          logger.info(s"probability for $rateRise = " + differential / rateRise )
      } else {
        // no change
        logger.info("differential is no change")
      }
    }

  }

}