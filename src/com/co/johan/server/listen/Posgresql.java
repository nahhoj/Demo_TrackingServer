package com.co.johan.server.listen;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class Posgresql
{
	static Logger log = Logger.getLogger(Posgresql.class.getName());
    private static Connection connection;
    
    public static void Connect() throws SQLException, ClassNotFoundException {
    	String server="localhost";
    	String port="5432";
    	String db="DemoTracking";
    	String user="postgres";
    	String passwd="A12345.";
    	Class.forName("org.postgresql.Driver");
		if (connection==null)
			connection = DriverManager.getConnection("jdbc:postgresql://" + server + ":" + port + "/" + db, user, passwd);
    }
    
    public static boolean insert(final String sql) throws SQLException, ClassNotFoundException {
    	Connect();
    	log.debug(sql);
    	System.out.println(new Date() + " - " + sql);
        Statement statement = connection.createStatement();
        return statement.execute(sql);
    }
    
    public static ResultSet select(final String query) throws SQLException, ClassNotFoundException {
    	Connect();
    	log.debug(query);
    	System.out.println(new Date() + " - " + query);
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
