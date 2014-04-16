package org.ccw.plutus.core.derivative.option.pricing



import org.ccw.plutus.core.derivative.option.model.VanillaOption
import org.joda.time.LocalDate


trait OptionStatistic {
  
}


trait OptionPricingModel {

  def getImpliedVolatility (option :VanillaOption, 
      currentDate :LocalDate, optionPrice :BigDecimal, underlyingPrice :BigDecimal) :BigDecimal
      
  def getOptionPrice (option :VanillaOption, 
      currentDate :LocalDate, spotPrice :BigDecimal,  
      volatility :BigDecimal) :BigDecimal 
}