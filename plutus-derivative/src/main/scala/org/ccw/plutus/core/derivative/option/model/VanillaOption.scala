package org.ccw.plutus.core.derivative.option.model

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.SettlementStyle._
import java.time.LocalDate

trait VanillaOption extends BaseOption {

  def payOffOnExpiry(spotPrice: BigDecimal): BigDecimal = {
    if (isCall && spotPrice >= strikePrice) {
      spotPrice - strikePrice
    } else if (isPut && strikePrice >= spotPrice) {
      strikePrice - spotPrice
    } else
      BigDecimal("0")
  }

}

trait VanillaCallOption extends VanillaOption {
  def isCall: Boolean = true
}

trait VanillaPutOption extends VanillaOption {
  def isCall: Boolean = false
}