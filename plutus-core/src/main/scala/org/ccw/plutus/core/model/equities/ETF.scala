package org.ccw.plutus.core.model.equities

import org.ccw.plutus.core.model.equities.EquityType._
import org.ccw.plutus.core.model.base.InstrumentType

class ETF(id :Long,  symbol :String,  displayName :String, 
    trackingIndex :String, exchange :String)  
	extends ListedEquity(id,  symbol,  displayName, ETF, exchange){

  
}