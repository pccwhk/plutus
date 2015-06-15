package org.ccw.plutus.core.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.io.Source
import org.xml.sax.InputSource
import scala.xml._
import scala.xml.parsing._

class HTML5Parser extends NoBindingFactoryAdapter {

  override def loadXML(source: InputSource, _p: SAXParser) = {
    loadXML(source)
  }

  def loadXML(source: InputSource) = {
    import nu.validator.htmlparser.{ sax, common }
    import sax.HtmlParser
    import common.XmlViolationPolicy

    val reader = new HtmlParser
    reader.setXmlPolicy(XmlViolationPolicy.ALLOW)
    reader.setContentHandler(this)
    reader.parse(source)
    rootElem
  }
}