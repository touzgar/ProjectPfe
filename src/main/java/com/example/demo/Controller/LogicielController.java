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

import com.example.demo.Model.Logiciel;
import com.example.demo.Model.Materiel;
import com.example.demo.Service.LogicielService;

@RestController
@RequestMapping("/api/logiciel")
@CrossOrigin("*")
public class LogicielController {
@Autowired
LogicielService logicielService;

@GetMapping("/getAll")
List<Logiciel> getAllLogiciels(){
	return logicielService.getAllLogiciel();
}
 @GetMapping("/get/{id}")
public Logiciel getLogicielById(@PathVariable("id") Long id) {
	return logicielService.getLogiciel(id);
}
 @PostMapping("/add")
 
 public Logiciel createLogiciel(@RequestBody Logiciel logiciel) {
     return logicielService.saveLogiciel(logiciel);
 }

 @PutMapping("/update/{id}")
public Logiciel updateLogiciel(@RequestBody Logiciel logiciel) {
    return logicielService.UpdateLogiciel(logiciel);
}
    @DeleteMapping("/delete/{id}")
public void deleteLogiciel(@PathVariable("id") Long id) {
    	logicielService.deleteLogicielById(id);
}



}
