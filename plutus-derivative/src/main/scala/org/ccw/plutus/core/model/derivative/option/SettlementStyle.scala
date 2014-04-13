package org.ccw.plutus.core.model.derivative.option

object SettlementStyle extends Enumeration{
  type SettlementStyle = Value
  val CASH_SETTLE, PHYSICAL_SETTLE = Value
}