package org.ccw.plutus.core.derivative.option.model

import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.SettlementStyle._
import java.util.Date
import org.joda.time.LocalDate

class EuropeanCallOption(
    val underlyingInstrument :Instrument,
    val strikePrice :BigDecimal, 
    val settlementDate :LocalDate, 
    val expiryDate :LocalDate) extends VanillaCallOption with EuropeanStyle {
    
	val settlementStyle = CASH_SETTLE
	
	def isExercisableNow(now :LocalDate) :Boolean = {
	  if (now.equals(expiryDate)) {
	    true
	  }
	  else false
	  
	}
	
  

}