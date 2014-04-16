package org.ccw.plutus.core.derivative.option.model

import java.util.Date

trait Exercisable{
  def isEarlyExercisable :Boolean
  def isExercisableNow(now :Date) :Boolean
}

trait EuropeanStyle extends Exercisable {
	def isEarlyExercisable :Boolean = false
}

trait AmericanStyle extends Exercisable {
	def isEarlyExercisable :Boolean = true
}