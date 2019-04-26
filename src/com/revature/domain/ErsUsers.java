package com.revature.domain;

import java.util.Date;

public class ErsUsers {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private Date sessionDate;
	private int empId;
	
	public ErsUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErsUsers(int id, String username, String password, String email, Date sessionDate, int empId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.sessionDate = sessionDate;
		this.empId = empId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "ErsUsers [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", sessionDate=" + sessionDate + ", empId=" + empId + "]";
	}
	
	

}
