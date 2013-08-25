package org.ccw.plutus.core.model.derivative.option

trait EuropeanOption {
	def isEarlyExercisable :Boolean = false
}

trait AmericanOption {
	def isEarlyExercisable :Boolean = true
}