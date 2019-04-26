package com.revature.domain;



public class Employee {

	private int employeeId; 
	private String firstName;
	private String lastName;
	private String email;
	private int managerId;
		
	public Employee() {
		
	}
	
	

	public Employee(int employeeId, String firsName, String lastName, String email, int managerId) {
		super();
		this.employeeId = employeeId;
		this.firstName = firsName;
		this.lastName = lastName;
		this.email = email;
		this.managerId = managerId;
	}



	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirsName() {
		return firstName;
	}

	public void setFirsName(String firsName) {
		this.firstName = firsName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firsName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", managerId=" + managerId + "]";
	}

	

}
