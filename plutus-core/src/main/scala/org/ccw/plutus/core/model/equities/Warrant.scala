package org.ccw.plutus.core.model.equities

import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.model.equities.EquityType._;
import org.ccw.plutus.core.model.base.InstrumentType

class Warrant(id :Long,  symbol :String,  displayName :String, 
    underlyingIntrument :Instrument)  
	extends Equity(id,  symbol,  displayName, WARRANT){

}