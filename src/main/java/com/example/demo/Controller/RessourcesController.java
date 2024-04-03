package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.Model.Ressources;
import com.example.demo.Model.Team;
import com.example.demo.Service.RessourceService;
import com.example.demo.Service.TeamService;

@RestController
@RequestMapping("/api/ressource")
@CrossOrigin("*")
public class RessourcesController {
@Autowired
RessourceService ressourceService;
@Autowired
TeamService teamService;

@GetMapping("/getAll")
List<Ressources> getAllRessources(){
	return ressourceService.getAllRessources();
}
 @GetMapping("/get/{id}")
public Ressources getRessourcesById(@PathVariable("id") Long id) {
	return ressourceService.getRessource(id);
}
 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateRessource(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
     try {
         Ressources existingRessource = ressourceService.getRessource(id);
         if (existingRessource == null) {
             return ResponseEntity.notFound().build();
         }

         // Update installation details from payload
         if (payload.containsKey("ressourceName")) {
        	    existingRessource.setResourceName((String) payload.get("ressourceName")); // Make sure this matches the key in the condition check
        	}

         // Save the updated installation information
         Ressources updatedRessource = ressourceService.saveRessource(existingRessource);

         return ResponseEntity.ok(updatedRessource);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("An error occurred while updating the Ressource: " + e.getMessage());
     }
 }

 @PostMapping("/add")
 public ResponseEntity<?> createRessource(@RequestBody Map<String, Object> payload) {
     try {
         // Extract fields from the payload
         String ressourceName = (String) payload.get("ressourcesName");
         String teamName = (String) payload.get("teamName");

         // Find the corresponding team by its name
         Team team = teamService.getTeamByName(teamName);
         if (team == null) {
             return ResponseEntity.badRequest().body("Team with name " + teamName + " not found");
         }

         // Create and save the new Resource
         Ressources resource = new Ressources();
         resource.setResourceName(ressourceName);
         resource.setTeam(team);

         Ressources savedResource = ressourceService.saveRessource(resource);
         return ResponseEntity.ok(savedResource);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating resource: " + e.getMessage());
     }
 }

    @DeleteMapping("/delete/{id}")
public void deleteRessources(@PathVariable("id") Long id) {
    	ressourceService.deleteRessourceById(id);
}
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Ressources> searchRessourcess(@RequestParam("name") String ressourcesName) {
        return ressourceService.searchByRessourcesName(ressourcesName);
    }

}
