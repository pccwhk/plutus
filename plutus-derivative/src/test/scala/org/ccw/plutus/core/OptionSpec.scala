package org.ccw.plutus.core

import org.ccw.plutus.core.model.base.Equity
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.model.derivative.option.AmericanCallOption
import java.util.Date

@RunWith(classOf[JUnitRunner])
class MyOptionSpec extends FlatSpec {

  "An Amercian Call " should " do something " in {
	  val stock = new Equity(1, "0005", "HSBC")
	  val call = new AmericanCallOption( stock, BigDecimal("80"), new Date)
  }

}