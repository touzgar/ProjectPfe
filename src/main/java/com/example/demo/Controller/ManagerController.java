package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Club;
import com.example.demo.Model.Manager;
import com.example.demo.Service.ManagerService;
@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*")

public class ManagerController {
@Autowired
ManagerService managerService;
	
	 @GetMapping("/getAll")
		List<Manager> getAllManagers(){
			return managerService.getAllManagers();
		}
		 @GetMapping("/get/{id}")
		public Manager getManagerById(@PathVariable("id") Long id) {
			return managerService.getManager(id);
		}
		 @PostMapping("/add")
		 
		 public Manager createManager(@RequestBody Manager manager) {
		     return managerService.saveManager(manager);
		 }

		 @PutMapping("/update/{id}")
		public Manager updateManager(@RequestBody Manager manager) {
		    return managerService.UpdateManager(manager);
		}
		    @DeleteMapping("/delete/{id}")
		public void deleteManager(@PathVariable("id") Long id) {
		    	managerService.deleteManagerById(id);
		}

}
