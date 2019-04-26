package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import com.revature.domain.ReimbursementRequest;
import com.revature.utilities.DAOUtilities;

import javax.ejb.Local;

public class ReimbursementRequestDAOImpl implements ReimbursementRequestDAO {
	
	Connection connection = null;
	Connection connectionPK = null;// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	Statement stmtpk = null;

	@Override
	public List<ReimbursementRequest> findAllReimbursementRequest() {
		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();	// Get our database connection from the manager
			String sql = "SELECT * FROM Reimbursements";			// Our SQL query
			stmt = connection.prepareStatement(sql);	// Creates the prepared statement from the query
			
			ResultSet rs = stmt.executeQuery();			// Queries the database

			// So long as the ResultSet actually contains results...
			while (rs.next()) {
				// We need to populate an Employee object with info for each row from our query result
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				// Each variable in our Employee object maps to a column in a row from our results.
				rreq.setId(rs.getInt("reim_id"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("reim_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}
				rreq.setEmpNumber(rs.getInt("emp_id"));
				// Finally we add it to the list of Employee objects returned by this query.
				rreq_list.add(rreq);	
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
		return rreq_list;
	}

	@Override
	public boolean addReimbursementRequest(ReimbursementRequest reimbursementRequest) {
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "INSERT INTO Reimbursements (reim_id, name, description, amount, reim_date, approved, emp_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)"; // Were using a lot of ?'s here...

			stmt = connection.prepareStatement(sql);
			// But that's okay, we can set them all before we execute
			
			stmt.setInt(1, getNewPK());
			stmt.setString(2, reimbursementRequest.getName());
			stmt.setString(3, reimbursementRequest.getDescription());
			stmt.setDouble(4, reimbursementRequest.getAmount());
			java.sql.Date sql_StartDate = new java.sql.Date( reimbursementRequest.getDate().getTime());
			stmt.setDate(5, sql_StartDate);
			if(reimbursementRequest.isApproved()==false) {
				stmt.setInt(6, 0);
			} else {
				stmt.setInt(6, 1);
			}
			stmt.setInt(7, reimbursementRequest.getEmpNumber());
			
			
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
	public boolean updateReimbursementRequest(ReimbursementRequest reimbursementRequest) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE Reimbursements SET name=?, description=?, amount=?, reim_date=?, approved=?, emp_id=? WHERE reim_id=?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, reimbursementRequest.getName());
			stmt.setString(2, reimbursementRequest.getDescription());
			stmt.setDouble(3, reimbursementRequest.getAmount());
			stmt.setDate(4, convertUtilToSql(reimbursementRequest.getDate()));
			if(reimbursementRequest.isApproved()==false) {
				stmt.setInt(5, 0);
			} else {
				stmt.setInt(5, 1);
			}
			stmt.setInt(6, reimbursementRequest.getEmpNumber());
			stmt.setInt(7, reimbursementRequest.getId());
			
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
	public boolean deleteReimbursementRequestId(int id) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE Reimbursements WHERE reim_id=?";
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

	@Override
	public List<ReimbursementRequest> findReimbursementRequestByEmployeeId(int empId) {
		
		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Reimbursements WHERE emp_id LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the employee id and wildcards, between ' '
			stmt.setString(1, Integer.toString(empId));	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				// Each variable in our ReimbursementRequest object maps to a column in a row from our results.
				rreq.setId(rs.getInt("reim_id"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("reim_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}			
				rreq.setEmpNumber(rs.getInt("emp_id"));
				// Finally we add it to the list of ReimbursementRequest objects returned by this query.
				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeResources();
		}
		return rreq_list;
	}
	
	@Override
	public ReimbursementRequest findReimbursementRequestByReimbursementId(int id) {
		
		ReimbursementRequest rreq = null;
		
			try {
				connection = DAOUtilities.getConnection();
				String sql = "SELECT * FROM Reimbursements WHERE reim_id = ?";
				stmt = connection.prepareStatement(sql);
				
				stmt.setString(1, Integer.toString(id));
				
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					int i = rs.getInt("approved");
					rreq = new ReimbursementRequest();
					rreq.setId(rs.getInt("reim_id"));
					rreq.setName(rs.getString("name"));
					rreq.setDescription(rs.getString("description"));
					rreq.setAmount(rs.getDouble("amount"));
					rreq.setDate(rs.getDate("reim_date"));
					rreq.setEmpNumber(rs.getInt("emp_id"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeResources();
			}
			
			return rreq;
		}

	

	@Override
	public List<ReimbursementRequest> findPendingReimbursementRequest() {
		
		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Reimbursements WHERE approved LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the approved value and wildcards, between ' '
			stmt.setString(1, Integer.toString(0));	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				// Each variable in our ReimbursementRequest object maps to a column in a row from our results.
				rreq.setId(rs.getInt("reim_id"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("reim_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}			
				rreq.setEmpNumber(rs.getInt("emp_id"));
				// Finally we add it to the list of ReimbursementRequest objects returned by this query.
				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeResources();
		}
		return rreq_list;
	}

	@Override
	public List<ReimbursementRequest> findResolvedReimbursementRequest() {
		List<ReimbursementRequest> rreq_list = new ArrayList<>();

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT * FROM Reimbursements WHERE approved LIKE ?";	// Note the ? in the query
			stmt = connection.prepareStatement(sql);
			
			// This command populate the 1st '?' with the not approved value and wildcards, between ' '
			stmt.setString(1, Integer.toString(1));	
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ReimbursementRequest rreq = new ReimbursementRequest();
				int i = rs.getInt("approved");

				// Each variable in our ReimbursementRequest object maps to a column in a row from our results.
				rreq.setId(rs.getInt("reim_id"));
				rreq.setName(rs.getString("name"));
				rreq.setDescription(rs.getString("description"));
				rreq.setAmount(rs.getDouble("amount"));
				rreq.setDate(rs.getDate("reim_date"));
					if (i==0) {
						rreq.setApproved(false);
					} else {
						rreq.setApproved(true);
					}			
				rreq.setEmpNumber(rs.getInt("emp_id"));
				// Finally we add it to the list of ReimbursementRequest objects returned by this query.
				rreq_list.add(rreq);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			closeResources();
		}
		return rreq_list;
	}

	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}
	
	private int getNewPK() throws SQLException {
		ResultSet rs=null;
		connectionPK = DAOUtilities.getConnection();
		String sql = "SELECT max(reim_id) as max FROM Reimbursements";
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
			if (connectionPK != null) {connectionPK.close();}
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
