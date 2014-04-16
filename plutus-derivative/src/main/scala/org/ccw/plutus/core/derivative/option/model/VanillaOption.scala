package org.ccw.plutus.core.derivative.option.model

import java.util.Date
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.option.model.SettlementStyle._


trait VanillaOption extends Exercisable{
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