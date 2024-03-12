package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Manager;
import com.example.demo.Repository.ManagerRepository;

@Service
public class ManagerServiceImpl implements ManagerService {
@Autowired
ManagerRepository managerRepository;
	@Override
	public Manager saveManager(Manager manager) {
		return managerRepository.save(manager);
	}

	@Override
	public Manager UpdateManager(Manager manager) {
		 return managerRepository.save(manager);
	}

	@Override
	public void deleteManager(Manager manager) {
		managerRepository.deleteAll();
	}

	@Override
	public void deleteManagerById(Long idManager) {
		managerRepository.deleteById(idManager);
		
	}

	@Override
	public Manager getManager(Long idManager) {
		
		return managerRepository.findById(idManager).get();
	}

	@Override
	public List<Manager> getAllManagers() {
		
		return managerRepository.findAll();
	}

}
