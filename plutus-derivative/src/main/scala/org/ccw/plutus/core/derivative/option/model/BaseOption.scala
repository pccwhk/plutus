package org.ccw.plutus.core.derivative.option.model

import java.time.LocalDate
import org.ccw.plutus.core.model.base.Instrument
import org.ccw.plutus.core.derivative.SettlementStyle._;

trait BaseOption {
  val strikePrice: BigDecimal
  val expiryDate: LocalDate
  val settlementDate: LocalDate
  val settlementStyle: SettlementStyle
  val underlyingInstrument: Instrument

  def isEarlyExercisable: Boolean
  def isExercisableNow(now: LocalDate): Boolean

  def isCall: Boolean
  def isPut: Boolean = !isCall

  def isExpired(date: LocalDate): Boolean = {
    date.isAfter(expiryDate)
  }

  def payOffOnExpiry(spotPrice: BigDecimal): BigDecimal
}

