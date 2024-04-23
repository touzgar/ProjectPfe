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


import com.example.demo.Model.Logiciel;

import com.example.demo.Model.Ressources;
import com.example.demo.Model.Team;
import com.example.demo.Service.LogicielService;
import com.example.demo.Service.RessourceService;
import com.example.demo.Service.TeamService;

@RestController
@RequestMapping("/api/logiciel")
@CrossOrigin("*")
public class LogicielController {
@Autowired
LogicielService logicielService;
@Autowired
TeamService teamService;

@GetMapping("/getAll")
List<Logiciel> getAllLogiciels(){
	return logicielService.getAllLogiciel();
}
 @GetMapping("/get/{id}")
public Logiciel getLogicielById(@PathVariable("id") Long id) {
	return logicielService.getLogiciel(id);
}
 @PostMapping("/add")
 public ResponseEntity<?> createLogiciel(@RequestBody Map<String, Object> payload) {
     try {
         // Extract fields from the payload
         String logicielName = (String) payload.get("logicielName");
         String type = (String) payload.get("type");
         Boolean status = (Boolean) payload.get("status");
         
         String teamName = (String) payload.get("teamName");

         // Find the corresponding team
         Team team = teamService.getTeamByName(teamName);
         if (team == null) {
             return ResponseEntity.badRequest().body("Team with name " + teamName + " not found");
         }

         // Create and save the new Logiciel
         Logiciel logiciel = new Logiciel();
         logiciel.setLogicielName(logicielName);
         logiciel.setType(type);
         logiciel.setStatus(status);
         
         logiciel.setTeam(team);

         Logiciel savedLogiciel = logicielService.saveLogiciel(logiciel);
         return ResponseEntity.ok(savedLogiciel);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating installation: " + e.getMessage());
     }
 }
 


 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateLogiciel(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
     try {
    	 Logiciel existingLogiciel = logicielService.getLogiciel(id);
         if (existingLogiciel == null) {
             return ResponseEntity.notFound().build();
         }

         // Update installation details from payload
         if (payload.containsKey("logicielName")) {
             existingLogiciel.setLogicielName((String) payload.get("logicielName"));
         }

         if (payload.containsKey("type")) {
             existingLogiciel.setType((String) payload.get("type"));
         }

         if (payload.containsKey("Status")) {
             existingLogiciel.setStatus((Boolean) payload.get("status"));
         }

        

         // Save the updated installation information
         Logiciel updatedLogiciel = logicielService.saveLogiciel(existingLogiciel);

         return ResponseEntity.ok(updatedLogiciel);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("An error occurred while updating the installation: " + e.getMessage());
     }
 }

   
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLogiciel(@PathVariable("id") Long id) {
        try {
        	logicielService.deleteLogicielById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Logiciel> searchInstallations(@RequestParam("name") String logicielName) {
        return logicielService.searchByLogicielName(logicielName);
    }



}
