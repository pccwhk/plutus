package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate

object BinomialOptionModel extends OptionPricingModel{
	
   def getImpliedVolatility (option :VanillaOption, 
      currentDate :LocalDate, optionPrice :BigDecimal, underlyingPrice :BigDecimal) :BigDecimal ={
     throw new Exception();
   }
      
  def getOptionPrice (option :VanillaOption, 
      currentDate :LocalDate,  volatility :BigDecimal) :BigDecimal ={
     throw new Exception();
   }
  
}