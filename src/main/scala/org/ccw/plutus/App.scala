package org.ccw.plutus

import org.ccw.plutus.core.model.Instrument
import org.ccw.plutus.core.model.derivative.option.AmericanCallOption
import java.util.Date

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("concat arguments = " + foo(args))
    
    val instrument = new Instrument()
    instrument.id = 1
    instrument.symbol = "aa"
      
      
    
    val call = new AmericanCallOption(
    		instrument, BigDecimal("25") , new Date())
    println(call.isPut)
  }

}
