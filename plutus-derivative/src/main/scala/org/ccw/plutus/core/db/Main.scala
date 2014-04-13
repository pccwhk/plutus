package org.ccw.plutus.core.db

import scala.io.Source

object Main {
  def main(args: Array[String]) {
    val a = Source.fromInputStream(getClass().getClassLoader().getResourceAsStream("config.txt")).mkString
    println(a)
  }
}