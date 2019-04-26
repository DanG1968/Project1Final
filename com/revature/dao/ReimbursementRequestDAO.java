package com.revature.dao;

import java.util.List;

import com.revature.domain.ReimbursementRequest;

public interface ReimbursementRequestDAO {
	
	public List<ReimbursementRequest> findReimbursementRequestByEmployeeId(int id);
	public ReimbursementRequest findReimbursementRequestByReimbursementId(int id);
	public List<ReimbursementRequest> findAllReimbursementRequest(); 
	public List<ReimbursementRequest> findPendingReimbursementRequest();
	public List<ReimbursementRequest> findResolvedReimbursementRequest();
	public boolean addReimbursementRequest(ReimbursementRequest reimbursementRequest);
	public boolean updateReimbursementRequest(ReimbursementRequest reimbursementRequest);
	public boolean deleteReimbursementRequestId(int id);

}
