package com.revature.dao;

import java.util.List;

import com.revature.domain.Manager;

public interface ManagerDAO {
	
	public List<Manager> getAllManagers();
	public List<Manager> getManagersByFirstName(String firstName);
	public List<Manager> getManagersByLastName(String lastName);
	public Manager getManagerById(Long id);
	
	public boolean addManager(Manager manager);
	public boolean updateManager(Manager manager);
	public boolean deleteManagerById(Long id);

}
