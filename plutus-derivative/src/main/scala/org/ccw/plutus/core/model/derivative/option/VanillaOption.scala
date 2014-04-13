package org.ccw.plutus.core.model.derivative.option

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.model.derivative.option.SettlementStyle._


trait VanillaOption {
	val strikePrice :BigDecimal
	val expiryDate :Date
	val settlementStyle :SettlementStyle
	val underlyingInstrument : Instrument
	
	
	def payOffOnExpiry(spotPrice :BigDecimal) :BigDecimal = {
	  if (isCall && spotPrice >= strikePrice) {
	    spotPrice - strikePrice
	  }
	  else if (isPut && strikePrice >= spotPrice){
	    strikePrice - spotPrice
	  }
	  else
	  BigDecimal("0")
	}
	def isCall :Boolean
	def isPut :Boolean = !isCall
}