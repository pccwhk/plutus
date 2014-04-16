package org.ccw.plutus.core.derivative.option.pricing


import java.util.Date
import org.ccw.plutus.core.derivative.option.model.VanillaOption


trait OptionStatistic {
  
}


trait OptionPricingModel {

  def getImpliedVolatility (option :VanillaOption, 
      currentDate :Date, optionPrice :BigDecimal, underlyingPrice :BigDecimal) :BigDecimal
      
  def getOptionPrice (option :VanillaOption, 
      currentDate :Date,  volatility :BigDecimal) :BigDecimal 
}