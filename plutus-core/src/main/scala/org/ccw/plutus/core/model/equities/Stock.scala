package org.ccw.plutus.core.model.equities

import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.model.base.InstrumentType
import org.ccw.plutus.core.model.equities.EquityType._;



class Stock(id :Long,  symbol :String,  displayName :String, exchange:String)  
	extends ListedEquity(id,  symbol,  displayName, EQUITY, exchange){
	
  
  }