package org.ccw.plutus.core.derivative.futures

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.derivative.option.model.AmericanCallOption
import org.ccw.plutus.core.derivative.option.model.AmericanPutOption
import org.ccw.plutus.core.derivative.option.pricing.BinomialOptionModel
import org.joda.time.LocalDate
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
import scala.xml._
import parsing._
import org.scalatest.Assertions._
import org.ccw.plutus.core.web.HTML5Parser
object InterestRateFutureSpec

@RunWith(classOf[JUnitRunner])
class InterestRateFutureSpec extends FlatSpec with BeforeAndAfter {

  val logger: Logger = LoggerFactory.getLogger(InterestRateFutureSpec.getClass())

  var futuresUrl = ""

  before {
    futuresUrl = "http://www.cmegroup.com/trading/interest-rates/stir/30-day-federal-fund.html?optionExpiration=N5"

  }

  "Futures Date Convertor " should " process date to next decade" in {
    val year2019 = new LocalDate(2019, 1, 31)

    val (month, year) = CMEFutures.getMonthFromCodes("G5", year2019)

    assert(year == 2025 && month == 2, s"Futures Date Convertor is not correctly converting G5 to 2025-01 in $year2019, wrong value is [$year-$month]")
  }

  "Futures Date Convertor " should " process date to same decade" in {

    val year2010 = new LocalDate(2010, 12, 31)

    val (month, year) = CMEFutures.getMonthFromCodes("Z5", year2010)

    assert(year == 2015 && month == 12, s"Futures Date Convertor is not correctly converting Z5 to 2015-12 in $year2010, wrong value is [$year-$month]")

  }

  it should " handle exception properly for invalid month code" in {
    intercept[Exception] {
      val (month, year) = CMEFutures.getMonthFromCodes("A5")
    }
  }

  "reading file from CME " should " work " in {

    val source1 = new org.xml.sax.InputSource(futuresUrl)
    val parser = new HTML5Parser

    val test = parser.loadXML(source1)

    println("--------------")
    val tdNode = (test \\ "td")
    tdNode.toSeq foreach {
      node =>
        {
          val IdSeq = (node \ "@id").toSeq
          IdSeq foreach { idNode =>
            val localId = idNode.text.trim
            if (localId.contains("_ZQ") && localId.contains("last")) {
              val start = localId.indexOf("_ZQ") + 3
              val end = start + 2
              val monthYearCode = localId.substring(start, end)
              val (month, year) = CMEFutures.getMonthFromCodes(monthYearCode)
              val lastPrice = (node \ "strong").text
              if (!lastPrice.equals("-")) {
                info(s"price is $lastPrice for $month, $year, $localId")
              }
            }
          }
        }
    }
  }

}