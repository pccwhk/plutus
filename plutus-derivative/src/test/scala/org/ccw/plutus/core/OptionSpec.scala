package org.ccw.plutus.core

import org.ccw.plutus.core.model.base.Equity
import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.ccw.plutus.core.model.derivative.option.AmericanCallOption
import java.util.Date

@RunWith(classOf[JUnitRunner])
class MyOptionSpec extends FlatSpec {

  "An Amercian Call " should " be early exercisable " in {
	  val stock = new Equity(1, "0005", "HSBC")
	  val americanCall = new AmericanCallOption( stock, BigDecimal("80"), new Date)
	  assertResult(true) {
		  americanCall.isEarlyExercisable
	  }
  }

}