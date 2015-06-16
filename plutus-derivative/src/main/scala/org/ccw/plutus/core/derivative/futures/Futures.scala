package org.ccw.plutus.core.derivative.futures

import org.ccw.plutus.core.derivative.SettlementStyle._;
import org.ccw.plutus.core.model.base.Instrument
import org.joda.time.LocalDate

class FutureContractSpec(
  val underlying: Instrument,
  val settlementStyle: SettlementStyle,
  val productCode: String,
  val monthCode: String,
  val exchange: String,
  val settlementDate: LocalDate,
  val expiryDate: LocalDate) {
}

trait Future {

  val contractSpec: FutureContractSpec

}