package org.ccw.plutus.core.derivative.futures

import org.joda.time.LocalDate
import org.ccw.plutus.core.util.StringUtil
import org.ccw.plutus.core.web.HTML5Parser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CMEFutures {

  val logger: Logger = LoggerFactory.getLogger(CMEFutures.getClass())

  val MONTH_CODES = List[String](
    "F", "G", "H", "J", "K", "M", "N", "Q", "U", "V", "X", "Z")

  def getMonthFromCodes(stringCode: String, currentDate: LocalDate): (Integer, Integer) = {
    val code = stringCode.trim
    if (code.length() != 2) {
      throw new Exception(s"Invalid Month Code - $code")
    } else {
      val month = code.take(1)
      val year = code.drop(1)

      val (isNumberic, n) = StringUtil.isNumberic(year)

      if (!isNumberic) {
        throw new Exception(s"Invalid Month Code - $code")
      } else {
        if (MONTH_CODES.contains(month)) {
          val index = MONTH_CODES.indexOf(month)
          val monthData = index + 1
          n match {
            case Some(x) => {
              val currentYear = currentDate.getYear
              val year = x.toInt

              val q = currentYear / 10
              val r = currentYear % 10

              if (r == 9 && year != 9) {
                (monthData, (q + 1) * 10 + year)
              } else {
                (monthData, (q * 10 + year))
              }
            }
            case _ => {
              throw new Exception(s"Invalid Month Code - $code")
            }
          }

        } else {
          throw new Exception(s"Invalid Month Code - $code")
        }
      }
    }
  }

  def getFuturePrice(futuresUrl: String, productCode: String, currentDate: LocalDate) = {
    val source1 = new org.xml.sax.InputSource(futuresUrl)
    val parser = new HTML5Parser
    val xmlNode = parser.loadXML(source1)
    val tdNode = (xmlNode \\ "td")
    val productKeyString = s"_$productCode"

    val list = for {
      node <- tdNode.toSeq
      IdSeq <- (node \ "@id").toSeq
      l <- for {
        idNode <- IdSeq
        localId = idNode.text.trim
        if (localId.contains(productKeyString) && localId.contains("last"))
        start = localId.indexOf(productKeyString) + 3
        end = start + 2
        monthYearCode = localId.substring(start, end)
        (month, year) = CMEFutures.getMonthFromCodes(monthYearCode, currentDate)
        lastPrice = (node \ "strong").text
        if (!lastPrice.equals("-"))
        if (StringUtil.isNumberic(lastPrice)._1)
        //  logger.info(s"$productCode - last price is $lastPrice for $month, $year, $localId")
      } yield ((year, month, BigDecimal(lastPrice)))
    } yield (l)
    list
  }

}