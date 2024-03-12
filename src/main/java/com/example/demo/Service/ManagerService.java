package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Manager;



public interface ManagerService {
	Manager saveManager(Manager manager);
	Manager UpdateManager(Manager manager);
	void deleteManager(Manager manager);
	void deleteManagerById(Long idManager);
	Manager getManager(Long idManager);
	List<Manager> getAllManagers();
}
