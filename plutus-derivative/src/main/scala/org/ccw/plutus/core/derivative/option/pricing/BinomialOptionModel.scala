package org.ccw.plutus.core.derivative.option.pricing

import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption

class BinomialOptionModel extends OptionPricingModel{
	
   def getImpliedVolatility (option :VanillaOption, 
      currentDate :Date, optionPrice :BigDecimal) :BigDecimal ={
     throw new Exception();
   }
      
  def getOptionPrice (option :VanillaOption, 
      currentDate :Date,  volatility :BigDecimal) :BigDecimal ={
     throw new Exception();
   }
  
}