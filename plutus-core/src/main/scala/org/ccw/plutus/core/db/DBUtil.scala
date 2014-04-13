package org.ccw.plutus.core.db

import com.jolbox.bonecp.BoneCP
import com.jolbox.bonecp.BoneCPConfig
import com.jolbox.bonecp.BoneCPDataSource

class DBUtil {
		val config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:h2:mem:test"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername("sa"); 
			config.setPassword("");
			config.setMinConnectionsPerPartition(1);
			config.setMaxConnectionsPerPartition(99);
			config.setPartitionCount(1);
		val connectionPool = new BoneCP(config); // setup the connection pool
		
		val ds = new BoneCPDataSource();  // create a new datasource object
 	ds.setJdbcUrl("jdbc:h2:mem:test");		// set the JDBC url
	ds.setUsername("sa");				// set the username
	ds.setPassword("");	
	ds.setMinConnectionsPerPartition(1);
	ds.setMaxConnectionsPerPartition(1);
	ds.setPartitionCount(2)

}

object Main {
  def main(args: Array[String]) {
	  val a = new DBUtil
	  val t = System.nanoTime()
	  val get = a.ds
	  
	   for( i <- 1 to 10){
	     val c = get.getConnection() 
	    
	     
		  //val c = get.getConnection()
		  println(i + " " + c.isValid(100))
		  
		  c.close()
		  if (c.isClosed()) {
		    println("connection is closed")
		  }
	     val  j = get.getTotalLeased()
	      
	     
	     println(s"leased = $j" )
		  
	  }
	  val j = (System.nanoTime() - t)/1000000000.0
	  println(j)
	  get.close()
  }
}