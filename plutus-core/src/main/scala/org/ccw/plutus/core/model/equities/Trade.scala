package org.ccw.plutus.core.model.equities

import org.joda.time.LocalDate


object TradeSide extends Enumeration {
  type TradeSide = Value
  val LONG, SELL, SHORT, COVER  = Value
}


class Trade(execution :LocalDate, equity :Equity, side :TradeSide.TradeSide,
    totalQty :BigDecimal, totalPrice :BigDecimal) {

}