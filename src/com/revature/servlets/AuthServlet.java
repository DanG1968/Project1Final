package com.revature.servlets;

import com.google.gson.Gson;
import com.revature.dao.UserDAO;
import com.revature.domain.LoginForm;
import com.revature.domain.User;
import com.revature.utilities.DAOUtilities;
import com.revature.utilities.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthServlet
 */

@WebServlet(urlPatterns = {"/api/login", "/api/logout"})
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //addDefaultUsers();
		BufferedReader reader = request.getReader();
		String result = reader.lines().collect(Collectors.joining());
		String path = request.getRequestURI().substring(request.getContextPath().length());
		LoginForm loginForm = new Gson().fromJson(result, LoginForm.class);
		UserDAO dao = DAOUtilities.getUserDao();

		try {
			User user = dao.getUserByUsernameAndPassword(loginForm.getUsername(), loginForm.getPassword());
			if (user != null) {
				String token = UUID.randomUUID().toString();
				Security.addUser(token, user);
				response.addHeader("Authorization", "Bearer " + token);
				response.getWriter().append("{ \"idToken\": \"" + token + "\" }");
			}
		} catch (Exception e) {
			response.getWriter().append(e.getMessage());
		}

		doGet(request, response);
	}

	private void addDefaultUsers() {
	    if (Security.users.size() == 0) {
            User user = new User();
            user.setUsername("manager");
            user.setPassword("manager");

            String uuid = UUID.randomUUID().toString();
            Security.addUser(uuid, user);
        }
    }

}
