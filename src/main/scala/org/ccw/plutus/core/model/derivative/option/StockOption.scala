package org.ccw.plutus.core.model.derivative.option

import java.util.Date
import org.ccw.plutus.core.model.Instrument

class AmericanCallOption(
    val underlyingInstrument :Instrument,
    val strikePrice :BigDecimal, 
    val expiryDate :Date) extends VanillaOption{
    
	val exerciseStyle = ExerciseStyle.AMERICAN
	def isCall :Boolean = true
  
  /*
	val expiryDate = _expiryDate
	val strikePrice = _strikePrice
	val underlyingInstrumenet = 
	def isCall = _isCall **/
}