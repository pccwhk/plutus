package org.ccw.plutus.core.derivative.option.pricing



import org.ccw.plutus.core.derivative.option.model.VanillaOption
import java.time.LocalDate


trait OptionStatistic {
  
}


trait OptionPricingModel {

  def getImpliedVolatility (option :VanillaOption, 
      currentDate :LocalDate, optionPrice :BigDecimal, underlyingPrice :BigDecimal) :BigDecimal
      
  def getOptionPrice (option :VanillaOption, 
      currentDate :LocalDate, spotPrice :BigDecimal,  annualInterestRate :BigDecimal,
      volatility :BigDecimal) :BigDecimal 
}