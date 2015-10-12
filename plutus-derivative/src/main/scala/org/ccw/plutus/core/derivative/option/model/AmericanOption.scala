package org.ccw.plutus.core.derivative.option.model

import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.SettlementStyle._
import java.time.LocalDate

class AmericanCallOption(
  val underlyingInstrument: Instrument,
  val strikePrice: BigDecimal,
  val settlementDate: LocalDate,
  val expiryDate: LocalDate)

  extends VanillaCallOption with AmericanStyle {

  def isExercisableNow(now: LocalDate): Boolean = {
    now.isBefore(expiryDate)
  }

  val settlementStyle = PHYSICAL_SETTLE

}

class AmericanPutOption(
  val underlyingInstrument: Instrument,
  val strikePrice: BigDecimal,
  val settlementDate: LocalDate,
  val expiryDate: LocalDate)

  extends VanillaPutOption with AmericanStyle {

  def isExercisableNow(now: LocalDate): Boolean = {
    now.isBefore(expiryDate)
  }

  val settlementStyle = PHYSICAL_SETTLE

}
