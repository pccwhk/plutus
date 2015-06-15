package org.ccw.plutus.core.util

object StringUtil {

  val numberMatcher = """([0-9]+)""".r

  val ZERO = BigDecimal("0")
  
  def isNumberic(s: String) :(Boolean, Option[BigDecimal]) = {
    s match {
      case numberMatcher(n) => {
        (true, Some(BigDecimal(n)))
      }
      case _ => {
        (false, None)
      }
    }

  }
}