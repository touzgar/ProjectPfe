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
import com.example.demo.Model.Materiel;
import com.example.demo.Service.MaterielService;

@RestController
@RequestMapping("/api/materiel")
@CrossOrigin("*")
public class MaterielController {
@Autowired
MaterielService materielService;
	@GetMapping("/getAll")
	List<Materiel> getAllMateriels(){
		return materielService.getAllMateriel();
	}
	 @GetMapping("/get/{id}")
	public Materiel getMaterielById(@PathVariable("id") Long id) {
		return materielService.getMateriel(id);
	}
	 @PostMapping("/add")
	 
	 public Materiel createMateriel(@RequestBody Materiel materiel) {
	     return materielService.saveMateriel(materiel);
	 }

	 @PutMapping("/update/{id}")
	public Materiel updateMateriel(@RequestBody Materiel materiel) {
	    return materielService.UpdateMateriel(materiel);
	}
	    @DeleteMapping("/delete/{id}")
	public void deleteMateriel(@PathVariable("id") Long id) {
	    	materielService.deleteMaterielById(id);
	}

}
