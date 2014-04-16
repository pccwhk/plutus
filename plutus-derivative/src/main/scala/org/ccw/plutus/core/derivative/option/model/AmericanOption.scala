package org.ccw.plutus.core.derivative.option.model

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.option.model.SettlementStyle._

class AmericanCallOption(
  val underlyingInstrument: Instrument,
  val strikePrice: BigDecimal,
  val expiryDate: Date)

  extends VanillaCallOption with AmericanStyle {

  def isExercisableNow(now: Date): Boolean = {
    true
  }

  val settlementStyle = PHYSICAL_SETTLE
  

}

