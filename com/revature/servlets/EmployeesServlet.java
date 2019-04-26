package com.revature.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.revature.dao.EmployeeDAO;
import com.revature.domain.Employee;
import com.revature.utilities.DAOUtilities;
import com.revature.utilities.Security;

@WebServlet("/api/employees/*")
public class EmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmployeeDAO dao = DAOUtilities.getEmployeeDAO();
		String path = request.getRequestURI().substring(request.getContextPath().length());
		if (path.equals("/api/employees")) {
			List<Employee> allEmployees = dao.getAllEmployees();
			response.getWriter().append(new Gson().toJson(allEmployees));
		} else if ((path.equals("/api/employees/id")) && (request.getParameter("id") != null)) {
			int id = Integer.parseInt(request.getParameter("id"));

			Employee employee = dao.getEmployeeById(id);

			response.getWriter().append(new Gson().toJson(employee));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String result = reader.lines().collect(Collectors.joining());
		String path = request.getRequestURI().substring(request.getContextPath().length());

		EmployeeDAO dao = DAOUtilities.getEmployeeDAO();

		String token = request.getHeader("Authorization").split(" ")[1];
		if (path.equals("/api/employees/add") && !Security.isManager(token)) {
		    response.setStatus(401);
            response.getWriter().append("{ \"message\": \"").append("only a manager can add a user\"}");
        }
		else if (path.equals("/api/employees/add"))
		{
			Gson gson = new Gson();

			Employee employee = gson.fromJson(result, Employee.class);
			dao.addEmployee(employee);

			response.getWriter().append(gson.toJson(employee));
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String result = reader.lines().collect(Collectors.joining());
		String path = request.getRequestURI().substring(request.getContextPath().length());

		EmployeeDAO dao = DAOUtilities.getEmployeeDAO();
		if (path.equals("/api/employees")) {
			Gson gson = new Gson();
			Employee employee = gson.fromJson(result, Employee.class);
			dao.updateEmployee(employee);
			response.getWriter().append(gson.toJson(employee));
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());

		EmployeeDAO dao = DAOUtilities.getEmployeeDAO();

		if (path.equals("/api/employees/delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			dao.deleteEmployeeById(id);
			response.getWriter().append("{\"id\": ").append(String.valueOf(id)).append("}");
		}
	}
}
