package org.ccw.plutus.core.db

import org.slf4j.Logger
import java.sql.Connection
import javax.sql.DataSource
import scala.collection.mutable.{ Map => MutableMap }
import org.slf4j.LoggerFactory

trait DBAdapter {
  
  def getConnection :Connection
}

object DBUtil

class DBUtil(dbAdapter :DBAdapter) {

  val logger: Logger = LoggerFactory.getLogger(DBUtil.getClass())

  val threadLocalConnections = new ThreadLocal[MutableMap[String, Connection]] {
    protected override def initialValue(): MutableMap[String, Connection] = {
      MutableMap[String, Connection]()
    }
  }

  def withThreadLocalConnection[T](connectionName: String)(f: Connection => T): T = {
    val (connection, isFirst) = getThreadLocalConnection(connectionName)
    try {
      f(connection)
    } finally {
      if (isFirst)
        releaseThreadLocalConnection(connectionName)
    }
  }

  // return (connnection, isConnectionGetter)
  def getThreadLocalConnection(connectionName: String): (Connection, Boolean) = {
    val m = threadLocalConnections.get()
    if (m.get(connectionName) == None) {
      val c = dbAdapter.getConnection
      logger.debug(s"Getting a new connection from pool for $connectionName ${Thread.currentThread()} {connection = ${c}}")
      m.put(connectionName, c)
      (c, true)
    } else {
      val c = m.get(connectionName).get
      logger.debug(s"Getting a new connection from ThreadLocal for $connectionName ${Thread.currentThread()} {connection = ${c}}")
      (c, false)
    }
  }

  def releaseThreadLocalConnection(connectionName: String) = {
    val m = threadLocalConnections.get()
    if (m.get(connectionName) == None) {
      // throw exception
      logger.debug(s"Cannot find a connection to release by ${Thread.currentThread()}")
      throw new Exception
    } else {
      // remove the connection from thread local 
      val c = m.remove(connectionName)
      if (c != None) {
        logger.debug(s"Trying to release connection by ${Thread.currentThread()} {connection = ${c.get}}")
        try {
          c.get.close
          logger.debug(s"Release connection by ${Thread.currentThread()} successfully")
        } finally {
          logger.debug(s"Fail to release connection by ${Thread.currentThread()}")
        }
      }
    }
  }
}