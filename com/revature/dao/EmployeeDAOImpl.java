package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.domain.Employee;
import com.revature.utilities.DAOUtilities;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	Connection connection = null;	// Our connection to the database
	Connection connectionPK = null;// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	Statement stmtpk = null;

	@Override
	public List<Employee> getAllEmployees() {
		
		List<Employee> employees = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Employees";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate an Employee object with info for each row from our query result
				Employee employee = new Employee();

				// Each variable in our Employee object maps to a column in a row from our results.
				employee.setEmployeeId(rs.getInt("employee_id"));
				employee.setFirsName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("manager_id"));
				
				// Finally we add it to the list of Employee objects returned by this query.
				employees.add(employee);				
			}
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed, 
			// or else we could wind up with a memory leak
			closeResources();
		}
		
		// return the list of Book objects populated by the DB.
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByFirstName(String firstName) {
					
			List<Employee> employees = new ArrayList<>();

			try {
				connection = DAOUtilities.getConnection();
				String sql = "SELECT * FROM Employees WHERE first_name LIKE ?";	// Note the ? in the query
				stmt = connection.prepareStatement(sql);
				
				// This command populate the 1st '?' with the firstName and wildcards, between ' '
				stmt.setString(1, "%" + firstName + "%");	
				
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Employee employee = new Employee();

					employee.setEmployeeId(rs.getInt("employee_id"));
					employee.setFirsName(rs.getString("first_name"));
					employee.setLastName(rs.getString("last_name"));
					employee.setEmail(rs.getString("email"));
					employee.setManagerId(rs.getInt("manager_id"));
					
					employees.add(employee);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				
				closeResources();
			}
			
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByLastName(String lastName) {
		List<Employee> employees = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Employees WHERE last_name LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the firstName and wildcards, between ' '
			stmt.setString(1, "%" + lastName + "%");	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();

				employee.setEmployeeId(rs.getInt("employee_id"));
				employee.setFirsName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("manager_id"));
				
				employees.add(employee);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeResources();
		}
		
		return employees;
		
	}

	@Override
	public Employee getEmployeeById(int id) {
		Employee employee = null;

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Employees WHERE employee_id = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				employee = new Employee();
				employee.setEmployeeId(rs.getInt("employee_id"));
				employee.setFirsName(rs.getString("first_name"));
				employee.setLastName(rs.getString("last_name"));
				employee.setEmail(rs.getString("email"));
				employee.setManagerId(rs.getInt("manager_id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return employee;
	}

	@Override
	public boolean addEmployee(Employee employee) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Employees (employee_id, first_name, last_name, email, manager_id) "
					+ "VALUES (?, ?, ?, ?, ?)"; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			// But that's okay, we can set them all before we execute
			stmt.setInt(1, getNewPK());
			stmt.setString(2, employee.getFirsName());
			stmt.setString(3, employee.getLastName());
			stmt.setString(4, employee.getEmail());
			stmt.setInt(5, employee.getManagerId());
			
			// If we were able to add our employee to the DB, we want to return true. 
			// This if statement both executes our query, and looks at the return 
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE Employees SET first_name=?, last_name=?, manager_id=?, email=? WHERE employee_id=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, employee.getFirsName());
			stmt.setString(2, employee.getLastName());
			stmt.setInt(3, employee.getManagerId());
			stmt.setString(4, employee.getEmail());
			stmt.setInt(5, employee.getEmployeeId());
			
			System.out.println(stmt);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deleteEmployeeById(int id) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE Employees WHERE employee_id=?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, id);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	
	private int getNewPK() throws SQLException {
		ResultSet rs=null;
		connectionPK = DAOUtilities.getConnection();
		String sql = "SELECT max(employee_id) as max FROM Employees";
		int new_pk = 0;
		try {
			stmtpk = connectionPK.createStatement();
			rs=stmtpk.executeQuery(sql);
			while(rs.next()){
				new_pk = (rs.getInt("max"))+1;
			}
		}
		catch (SQLException e) {e.printStackTrace();}
//		finally{
//			if (stmtpk != null) {stmtpk.close();}
//			if (connectionPK != null) {connectionPK.close();}
//		}
		return new_pk;
		
	}
	
	// Closing all resources is important, to prevent memory leaks. 
		// Ideally, you really want to close them in the reverse-order you open them
		private void closeResources() {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				System.out.println("Could not close statement!");
				e.printStackTrace();
			}
			
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.out.println("Could not close connection!");
				e.printStackTrace();
			}
		}

}
