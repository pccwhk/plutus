package org.ccw.plutus.core.db.adapter

import com.jolbox.bonecp.BoneCP
import com.jolbox.bonecp.BoneCPConfig
import com.jolbox.bonecp.BoneCPDataSource
import org.ccw.plutus.core.db.DBAdapter

class BonecpAdapter extends DBAdapter {
  val config = new BoneCPConfig();
  config.setJdbcUrl("jdbc:h2:mem:test"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
  config.setUsername("sa");
  config.setPassword("");
  config.setMinConnectionsPerPartition(1);
  config.setMaxConnectionsPerPartition(99);
  config.setPartitionCount(1);
  val connectionPool = new BoneCP(config); // setup the connection pool

  def getConnection = connectionPool.getConnection

  //		val ds = new BoneCPDataSource();  // create a new datasource object
  // 	ds.setJdbcUrl("jdbc:h2:mem:test");		// set the JDBC url
  //	ds.setUsername("sa");				// set the username
  //	ds.setPassword("");	
  //	ds.setMinConnectionsPerPartition(1);
  //	ds.setMaxConnectionsPerPartition(1);
  //	ds.setPartitionCount(2)

}

