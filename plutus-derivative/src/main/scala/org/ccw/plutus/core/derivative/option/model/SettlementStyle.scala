package org.ccw.plutus.core.derivative.option.model

object SettlementStyle extends Enumeration{
  type SettlementStyle = Value
  val CASH_SETTLE, PHYSICAL_SETTLE = Value
}