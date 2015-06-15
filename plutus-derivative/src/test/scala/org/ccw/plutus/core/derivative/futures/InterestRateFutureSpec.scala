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


object InterestRateFutureSpec

class HTML5Parser extends NoBindingFactoryAdapter {

  override def loadXML(source: InputSource, _p: SAXParser) = {
    loadXML(source)
  }

  def loadXML(source: InputSource) = {
    import nu.validator.htmlparser.{ sax, common }
    import sax.HtmlParser
    import common.XmlViolationPolicy

    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }
}

@RunWith(classOf[JUnitRunner])
class InterestRateFutureSpec extends FlatSpec with BeforeAndAfter {

  val logger: Logger = LoggerFactory.getLogger(InterestRateFutureSpec.getClass())

  var futuresUrl = ""

  before {
    futuresUrl = "http://www.cmegroup.com/trading/interest-rates/stir/30-day-federal-fund.html?optionExpiration=N5"

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
              val start = localId.indexOf("_ZQ") +3
              val end = start + 2
              val monthYearCode = localId.substring(start , end)
              val (month, year) = CMEFutures.getMonthFromCodes(monthYearCode)
              val lastPrice = (node \ "strong").text
              if (!lastPrice.equals("-")) {
                println(s"price is $lastPrice for $month, $year, $localId")
              }
            }
          }
        }
    }
  }

}