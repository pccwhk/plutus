package org.ccw.plutus.core.model.base

object InstrumentType extends Enumeration {
  type InstrumentType = Value
  val EQUITY, FIXED_INCOME, INTEREST_RATE, SWAP, FX  = Value
}
