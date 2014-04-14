package org.ccw.plutus.core.model.derivative.option

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.model.derivative.option.SettlementStyle._

class AmericanCallOption(
  val underlyingInstrument: Instrument,
  val strikePrice: BigDecimal,
  val expiryDate: Date)

  extends VanillaOption with AmericanOption {

  def isExercisableNow(now: Date): Boolean = {
    true
  }

  val settlementStyle = PHYSICAL_SETTLE
  def isCall: Boolean = true

}

