package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.Model.Installation;
import com.example.demo.Model.Logiciel;
import com.example.demo.Model.Materiel;
import com.example.demo.Model.Ressources;
import com.example.demo.Model.Team;
import com.example.demo.Service.MaterielService;
import com.example.demo.Service.RessourceService;
import com.example.demo.Service.TeamService;

@RestController
@RequestMapping("/api/materiel")
@CrossOrigin("*")
public class MaterielController {
@Autowired
MaterielService materielService;
@Autowired
TeamService teamService;
	@GetMapping("/getAll")
	List<Materiel> getAllMateriels(){
		return materielService.getAllMateriel();
	}
	 @GetMapping("/get/{id}")
	public Materiel getMaterielById(@PathVariable("id") Long id) {
		return materielService.getMateriel(id);
	}
	 @PostMapping("/add")
	 public ResponseEntity<?> createMateriel(@RequestBody Map<String, Object> payload) {
	     try {
	         // Extract fields from the payload
	         String materielName = (String) payload.get("materielName");
	         String type = (String) payload.get("type");
	         Boolean status = (Boolean) payload.get("status");
	         
	         String teamName = (String) payload.get("teamName");

	         // Find the corresponding team
	         Team team = teamService.getTeamByName(teamName);
	         if (team == null) {
	             return ResponseEntity.badRequest().body("Team with name " + teamName + " not found");
	         }

	         // Create and save the new Logiciel
	         Materiel materiel = new Materiel();
	         materiel.setMaterielName(materielName);
	         materiel.setType(type);
	         materiel.setStatus(status);
	         
	         materiel.setTeam(team);

	         Materiel savedMateriel = materielService.saveMateriel(materiel);
	         return ResponseEntity.ok(savedMateriel);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating materiel: " + e.getMessage());
	     }
	 }
	 


	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateMateriel(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	     try {
	    	 Materiel existingMateriel = materielService.getMateriel(id);
	         if (existingMateriel == null) {
	             return ResponseEntity.notFound().build();
	         }

	         // Update installation details from payload
	         if (payload.containsKey("materielName")) {
	             existingMateriel.setMaterielName((String) payload.get("materielName"));
	         }

	         if (payload.containsKey("type")) {
	             existingMateriel.setType((String) payload.get("type"));
	         }

	         if (payload.containsKey("Status")) {
	             existingMateriel.setStatus((Boolean) payload.get("status"));
	         }

	        

	         // Save the updated installation information
	         Materiel updatedMateriel = materielService.saveMateriel(existingMateriel);

	         return ResponseEntity.ok(updatedMateriel);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("An error occurred while updating the materiel: " + e.getMessage());
	     }
	 }

	   
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteMateriel(@PathVariable("id") Long id) {
	        try {
	        	materielService.deleteMaterielById(id);
	            return ResponseEntity.ok().build();
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	        }
	    }

	    @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Materiel> searchMateriels(@RequestParam("name") String materielName) {
	        return materielService.searchByMaterielName(materielName);
	    }


}
