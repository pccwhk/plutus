package org.ccw.plutus.core.derivative.option.model

import java.util.Date
import org.joda.time.LocalDate

trait ExerciseStyle{
  def isEarlyExercisable :Boolean
}

trait EuropeanStyle extends ExerciseStyle {
	def isEarlyExercisable :Boolean = false
}

trait AmericanStyle extends ExerciseStyle {
	def isEarlyExercisable :Boolean = true
}