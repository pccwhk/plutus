package org.ccw.plutus.core.model.derivative.option

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument

class AmericanCallOption (
    val underlyingInstrument :Instrument,
    val strikePrice :BigDecimal, 
    val expiryDate :Date) extends VanillaOption with AmericanOption{
    
	val settlementStyle = SettlementStyle.PHYSICAL_SETTLE
	def isCall :Boolean = true
  
  /*
	val expiryDate = _expiryDate
	val strikePrice = _strikePrice
	val underlyingInstrumenet = 
	def isCall = _isCall **/
}

class EuropeanCallWarrant(
    val underlyingInstrument :Instrument,
    val strikePrice :BigDecimal, 
    val expiryDate :Date) extends VanillaOption with EuropeanOption {
    
	val settlementStyle = SettlementStyle.CASH_SETTLE
	def isCall :Boolean = true
  
  /*
	val expiryDate = _expiryDate
	val strikePrice = _strikePrice
	val underlyingInstrumenet = 
	def isCall = _isCall **/
}