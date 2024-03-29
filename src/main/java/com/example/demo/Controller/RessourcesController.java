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

import com.example.demo.Model.Ressources;

import com.example.demo.Service.RessourceService;

@RestController
@RequestMapping("/api/ressource")
@CrossOrigin("*")
public class RessourcesController {
@Autowired
RessourceService ressourceService;

@GetMapping("/getAll")
List<Ressources> getAllRessources(){
	return ressourceService.getAllRessources();
}
 @GetMapping("/get/{id}")
public Ressources getRessourcesById(@PathVariable("id") Long id) {
	return ressourceService.getRessource(id);
}
 @PostMapping("/add")
 
 public Ressources createRessources(@RequestBody Ressources ressource) {
     return ressourceService.saveRessource(ressource);
 }

 @PutMapping("/update/{id}")
public Ressources updateRessources(@RequestBody Ressources ressource) {
    return ressourceService.UpdateRessource(ressource);
}
    @DeleteMapping("/delete/{id}")
public void deleteRessources(@PathVariable("id") Long id) {
    	ressourceService.deleteRessourceById(id);
}

}
