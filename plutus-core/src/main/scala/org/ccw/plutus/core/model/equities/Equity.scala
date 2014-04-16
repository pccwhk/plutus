package org.ccw.plutus.core.model.equities

import org.ccw.plutus.core.model.base.InstrumentType
import org.ccw.plutus.core.model.base.Instrument

object EquityType extends Enumeration {
  type EquityType = Value
  val EQUITY, ETF, WARRANT = Value
}


class Equity(id :Long,  symbol :String,  displayName :String, 
    equityType :EquityType.EquityType)  
	extends Instrument(id,  symbol,  displayName, InstrumentType.EQUITY){

}