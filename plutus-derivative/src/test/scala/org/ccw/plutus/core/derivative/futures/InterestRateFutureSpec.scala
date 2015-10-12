package org.ccw.plutus.core.derivative.futures

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.time.LocalDate
import org.scalatest.BeforeAndAfter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URL
import java.io.File
import scala.io.Source
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.InetSocketAddress
import scala.collection.mutable.Queue
import org.xml.sax.InputSource
import org.scalatest.Assertions._
import org.ccw.plutus.core.web.HTML5Parser
//import org.ccw.plutus.core.derivative.futures.InterestRateFuture

object InterestRateFutureSpec

@RunWith(classOf[JUnitRunner])
class InterestRateFutureSpec extends FlatSpec with BeforeAndAfter {



  val logger: Logger = LoggerFactory.getLogger(InterestRateFutureSpec.getClass())

  var interestRateFuturesUrl = ""
  var euroDollarFuturesUrl = ""
  val today = LocalDate.now()

  before {
    interestRateFuturesUrl = "http://www.cmegroup.com/trading/interest-rates/stir/30-day-federal-fund.html"
    euroDollarFuturesUrl = "http://www.cmegroup.com/trading/interest-rates/stir/eurodollar.html"
  }

  "Futures Date Convertor " should " process date to next decade" in {
    val year2019 = LocalDate.of(2019, 1, 31)

    val (month, year) = CMEFutures.getMonthFromCodes("G5", year2019)

    assert(year == 2025 && month == 2, s"Futures Date Convertor is not correctly converting G5 to 2025-01 in $year2019, wrong value is [$year-$month]")
  }

  it should " process date to same decade" in {

    val year2010 = LocalDate.of(2010, 12, 31)

    val (month, year) = CMEFutures.getMonthFromCodes("Z5", year2010)

    assert(year == 2015 && month == 12, s"Futures Date Convertor is not correctly converting Z5 to 2015-12 in $year2010, wrong value is [$year-$month]")

  }

  it should " handle exception properly for invalid month code" in {
    intercept[Exception] {
      val (month, year) = CMEFutures.getMonthFromCodes("A5", today)
    }
  }

  "CME Futures " should " get last price for Interest Rate Product - ZQ " in {
    CMEFutures.getFuturePrice(interestRateFuturesUrl, "ZQ", today)
  }

  it should " get last price for EURODOLLER - GU " in {
    CMEFutures.getFuturePrice(euroDollarFuturesUrl, "GU", today)
  }

  "InterestRateFuture probability " should " calculate the rate rise probability" in {
    val r = CMEFutures.getFuturePrice(euroDollarFuturesUrl, "ZQ", today)
    r foreach {
      case (year, month, p) =>{
        InterestRateFuture.impliedProbabilityFromFuturesPrice(p.toDouble, 0.25, LocalDate.now)
      }
    }
  }

}