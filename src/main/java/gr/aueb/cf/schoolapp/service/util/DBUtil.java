package gr.aueb.cf.schoolapp.service.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {
	
	private final static BasicDataSource ds = new BasicDataSource();	// Container with connections
	private static Connection connection;
	
	static {
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/school6db?serverTimeZone=UTC");
		ds.setUsername("userdb6");
		ds.setPassword(System.getenv("PASS_DB6"));	// environmental variable PASS_CB6 with user's password

		// Connection pool config
		ds.setInitialSize(10);	// 10 connections in pool
		ds.setMaxTotal(50);
		ds.setMinIdle(8);		// Minimum 8 idle connections in pool
		ds.setMaxIdle(10);		// Maximum 10 idle connections in pool
	}
	
	/**
	 * No instance of this class should be available (Utility class)
	 */
	private DBUtil() {
	}
	
	public static Connection getConnection() throws SQLException{
		connection = ds.getConnection();		
		return connection;
	}
	
	public static void closeConnection() {
		try {
			if (connection != null) connection.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
