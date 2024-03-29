package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Installation;
import com.example.demo.Model.Ressources;
import com.example.demo.Service.InstallationService;

@RestController
@RequestMapping("/api/installation")
@CrossOrigin("*")
public class InstallationController {
@Autowired
InstallationService installationService;
@GetMapping("/getAll")
List<Installation> getAllInstallations(){
	return installationService.getAllInstallation();
}
 @GetMapping("/get/{id}")
public Installation getInstallationById(@PathVariable("id") Long id) {
	return installationService.getInstallation(id);
}
 @PostMapping("/add")
 
 public Installation createInstallation(@RequestBody Installation installation) {
     return installationService.saveInstallation(installation);
 }

 @PutMapping("/update/{id}")
public Installation updateInstallation(@RequestBody Installation installation) {
    return installationService.UpdateInstallation(installation);
}
    @DeleteMapping("/delete/{id}")
public void deleteInstallation(@PathVariable("id") Long id) {
    	installationService.deleteInstallationById(id);
}

}
