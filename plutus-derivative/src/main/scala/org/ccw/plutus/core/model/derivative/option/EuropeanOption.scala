package org.ccw.plutus.core.model.derivative.option

import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.model.derivative.option.SettlementStyle._
import java.util.Date

class EuropeanCallOption(
    val underlyingInstrument :Instrument,
    val strikePrice :BigDecimal, 
    val expiryDate :Date) extends VanillaOption with EuropeanOption {
    
	val settlementStyle = CASH_SETTLE
	def isCall :Boolean = true
  

}