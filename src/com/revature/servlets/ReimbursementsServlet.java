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
import com.revature.dao.ReimbursementRequestDAO;
import com.revature.domain.ReimbursementRequest;
import com.revature.utilities.DAOUtilities;


/**
 * Servlet implementation class ReimbursementsServlet
 */

@WebServlet("/api/reimbursement-requests/*")
public class ReimbursementsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     /**
     * @see HttpServlet#HttpServlet()
     */
    public ReimbursementsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ReimbursementRequestDAO dao = DAOUtilities.getReimbursementRequestDAO();
		String path = request.getRequestURI().substring(request.getContextPath().length());
//	    getMyPending(): List<ReimbursementRequest>
		if (path.equals("/api/reimbursement-requests")) {
			List<ReimbursementRequest> allEmployees = dao.findAllReimbursementRequest();
			response.getWriter().append(new Gson().toJson(allEmployees));
		} else if (path.equals("/api/reimbursement-requests/my/empId") && (request.getParameter("empId") != null)) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			List<ReimbursementRequest> allRequestsByEmpId = dao.findReimbursementRequestByEmployeeId(empId);
			response.getWriter().append(new Gson().toJson(allRequestsByEmpId));
		} else if (path.equals("/api/reimbursement-requests/my/id") && (request.getParameter("id") != null)) {
			int id = Integer.parseInt(request.getParameter("id"));
			ReimbursementRequest allRequestsById = dao.findReimbursementRequestByReimbursementId(id);
			response.getWriter().append(new Gson().toJson(allRequestsById));
		} else if (path.equals("/api/reimbursement-requests/my/pending")) {
			List<ReimbursementRequest> requestsPending = dao.findPendingReimbursementRequest();
			response.getWriter().append(requestsPending.toString());
//		getMyResolved(): List<ReimbursementRequest>
		} else if (path.equals("/api/reimbursement-requests/my/resolved")) {
			List<ReimbursementRequest> requestsResolved = dao.findResolvedReimbursementRequest();
			response.getWriter().append(requestsResolved.toString());
//		} else if ((request.getParameter("reim_id") != null) && (path.equals("/api/reimbursement-requests/approve"))) {
//			String appcode = request.getParameter("appcode");
//			String reim_id = request.getParameter("reim_id");
//			int int_reim_id = Integer.parseInt(reim_id);
//			ReimbursementRequest rreq = dao.findReimbursementRequestByReimbursementId(int_reim_id);
////			approve(id): Request
//			if (appcode.equals("1")) {
////				dao.updateReimbursementRequest(rreq,1);
////				response.getWriter().append("Approved: ").append(rreq.toString());
//				if (dao.updateReimbursementRequest(rreq, 1)) {
//					response.getWriter().append("Approved: ").append(rreq.toString());
//				}
//			} else if (appcode.equals("0")) {
////			deny(id): Request
//				dao.updateReimbursementRequest(rreq,0);
//				response.getWriter().append("Denied: ").append(rreq.toString());
//			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String result = reader.lines().collect(Collectors.joining());
		String path = request.getRequestURI().substring(request.getContextPath().length());
		ReimbursementRequestDAO dao = DAOUtilities.getReimbursementRequestDAO();
		//	    create(request): ReimbursementRequest
		if (path.equals("/api/reimbursement-requests")) {
			Gson gson = new Gson();
			ReimbursementRequest rreq = gson.fromJson(result, ReimbursementRequest.class);
			System.out.println("Incoming reimbursement: " + rreq);
			dao.addReimbursementRequest(rreq);
			response.getWriter().append(gson.toJson(rreq));
		}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String result = reader.lines().collect(Collectors.joining());
		String path = request.getRequestURI().substring(request.getContextPath().length());

		ReimbursementRequestDAO dao = DAOUtilities.getReimbursementRequestDAO();
		if (path.equals("/api/reimbursement-requests")) {
			//int id = Integer.parseInt(request.getParameter("id"));
			Gson gson = new Gson();
			ReimbursementRequest reimbursementRequest = gson.fromJson(result, ReimbursementRequest.class);
			dao.updateReimbursementRequest(reimbursementRequest);
			response.getWriter().append(gson.toJson(reimbursementRequest));
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI().substring(request.getContextPath().length());

		ReimbursementRequestDAO dao = DAOUtilities.getReimbursementRequestDAO();

		if (path.equals("/api/reimbursement-requests/delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			dao.deleteReimbursementRequestId(id);
			response.getWriter().append("{\"id\": ").append(String.valueOf(id)).append("}");
		}
	}

}
