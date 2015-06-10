package org.ccw.plutus.core.db.adapter

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.ccw.plutus.core.db.DBAdapter

class HikariAdapter extends DBAdapter{
  val config = new HikariConfig()
  config.setJdbcUrl("jdbc:h2:mem:test"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
  config.setUsername("sa");
  config.setPassword("");
  config.addDataSourceProperty("cachePrepStmts", "true");
  config.addDataSourceProperty("prepStmtCacheSize", "250");
  config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
  config.addDataSourceProperty("useServerPrepStmts", "true");

  val ds = new HikariDataSource(config); // create a new datasource object

  def getConnection = ds.getConnection
}

