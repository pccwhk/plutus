package org.ccw.plutus.core.model.derivative.option

import java.util.Date

trait Exercisable{
  def isEarlyExercisable :Boolean
  def isExercisableNow(now :Date) :Boolean
}

trait EuropeanOption extends Exercisable {
	def isEarlyExercisable :Boolean = false
}

trait AmericanOption extends Exercisable {
	def isEarlyExercisable :Boolean = true
}