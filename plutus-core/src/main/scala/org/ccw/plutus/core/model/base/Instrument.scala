package org.ccw.plutus.core.model.base

import InstrumentType._


abstract class Instrument (
	val id :Long,
	val symbol :String,
	val displayName :String,
	val instrumentType : InstrumentType){
  
}

