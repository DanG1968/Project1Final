package com.revature.dao;

import com.revature.domain.User;
import com.revature.utilities.DAOUtilities;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    Connection connection = null;    // Our connection to the database
    Connection connectionPK = null;// Our connection to the database
    PreparedStatement stmt = null;    // We use prepared statements to help protect against SQL injection
    Statement stmtpk = null;

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;

        try {
            connection = DAOUtilities.getConnection();
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setId(rs.getInt("user_id"));
                user.setRole(rs.getString("role"));
                user.setUsername(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return user;
    }

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
