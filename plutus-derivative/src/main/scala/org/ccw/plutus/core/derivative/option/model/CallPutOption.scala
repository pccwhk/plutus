package org.ccw.plutus.core.derivative.option.model

trait VanillaCallOption extends VanillaOption {
	def isCall: Boolean = true
}

trait VanillaPutOption extends VanillaOption {
	def isCall: Boolean = false
}