package org.ccw.plutus.core.model.base



class Equity(id :Long,  symbol :String,  displayName :String)  
	extends Instrument(id,  symbol,  displayName, InstrumentType.EQUITY){
  
  
  }