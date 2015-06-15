package org.ccw.plutus.core.derivative.futures

import org.joda.time.LocalDate
import org.ccw.plutus.core.util.StringUtil

object CMEFutures {
  val today = new LocalDate

  val MONTH_CODES = List[String](
    "F", "G", "H", "J", "K", "M", "N", "Q", "U", "V", "X", "Z")

  def getMonthFromCodes(stringCode: String, currentDate: LocalDate = today): (Integer, Integer) = {
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
                (monthData,  (q + 1)* 10 + year)
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
}