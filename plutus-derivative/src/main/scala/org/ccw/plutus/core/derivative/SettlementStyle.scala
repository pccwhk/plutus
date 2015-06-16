package org.ccw.plutus.core.derivative

object SettlementStyle extends Enumeration{
  type SettlementStyle = Value
  val CASH_SETTLE, PHYSICAL_SETTLE = Value
}