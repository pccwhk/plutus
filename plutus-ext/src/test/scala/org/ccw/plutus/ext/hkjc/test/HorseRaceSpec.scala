package org.ccw.plutus.ext.hkjc.test

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.ccw.plutus.core.web.HTML5Parser
import org.joda.time.LocalDate
import scala.collection.mutable.HashMap
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class HorseRaceSpec extends FlatSpec with BeforeAndAfter {
  var url = ""
  before {
    url = "http://bet.hkjc.com/racing/getXML.aspx?type=jcbwracing_progwinodds&"

  }

  it should "parse the winning bet" in {
    val venue = "HV"
    val sb = new StringBuilder(url)
    sb.append("date=").append(new LocalDate().toString("dd-MM-yyyy")).append("&venue=").append(venue)
    sb.append("&raceno=7").append("&cur=").append("220")

    var map = new HashMap[String, Array[String]]()
    
    val temp = sb.toString()
    for (i <- 1 to 10) {
      var url =  temp
      if (i <10) {
        url = url + 0
      }
      
      val source1 = new org.xml.sax.InputSource(temp + i)
      val parser = new HTML5Parser
      val xmlNode = parser.loadXML(source1)
      val outNode = (xmlNode \\ "out")
      
      outNode.toSeq foreach {
        n =>{
          val time = (n \ "@time").text.trim
          val bet = n.text.trim.split(",")
          map.put(time, bet)
        }
      }
      
    }
    map foreach {
      case (k, v) =>{
        println(k, v(0))
      }
    }
  }
}