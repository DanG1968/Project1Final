package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.dao.*;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "dan";
	private static final String CONNECTION_PASSWORD = "dan";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}
	
	public static EmployeeDAO getEmployeeDAO() {
		return new EmployeeDAOImpl();
	}
	
	public static ManagerDAO getManagerDAO() {
		return new ManagerDAOImpl();
	}
	
	public static ReimbursementRequestDAO getReimbursementRequestDAO() {
		return new ReimbursementRequestDAOImpl();
	}

	public static UserDAO getUserDao() {
		return new UserDAOImpl();
	}
}
