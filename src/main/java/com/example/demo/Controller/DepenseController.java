package com.example.demo.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Depense;
import com.example.demo.Service.DepenseService;

@RestController
@RequestMapping("/api/depense")
@CrossOrigin("*")
public class DepenseController {
@Autowired
DepenseService depenseService;


@GetMapping("/getAll")
	List<Depense> getAllDepenses(){
		return depenseService.getAllDepenses();
	}
	 @GetMapping("/get/{id}")
	public Depense getDepenseById(@PathVariable("id") Long id) {
		return depenseService.getDepense(id);
	}
	    @DeleteMapping("/delete/{id}")
	public void deleteDepense(@PathVariable("id") Long id) {
	    	depenseService.deleteDepenseById(id);
	}
	 
	 @PostMapping("/add")
	 public ResponseEntity<?> createDepense(@RequestBody Map<String, Object> payload) {
	     try {
	         
	         String description = (String) payload.get("description");
	         String depenseName = (String) payload.get("Depense");
	         Double montant = ((Number) payload.get("montant")).doubleValue();
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date date = dateFormat.parse((String) payload.get("date"));
	           
	        

	          Depense depense = new Depense();
	          depense.setDepenseName (depenseName);
	          depense.setDescription(description);
	          depense.setDate(date);
	          depense.setMontant(montant);
	         // Use the service to handle the logic of adding club and coach by name
	          Depense savedDepense = depenseService.saveDepense(depense);
	         return ResponseEntity.ok(savedDepense);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateDepense(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	        try {
	        	Depense existingDepense = depenseService.getDepense(id);
	            if (existingDepense == null) {
	                return ResponseEntity.notFound().build();
	            }

	            // Update player details from payload
	            String depenseName = (String) payload.get("depenseName");
	            if (depenseName != null) existingDepense.setDepenseName(depenseName);
	            
	            String description = (String) payload.get("description");
	            if (description != null) existingDepense.setDescription(description);
	            
	            if (payload.containsKey("salary")) {
	                Double montant = ((Number) payload.get("montant")).doubleValue();
	                existingDepense.setMontant(montant);
	            }

	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            if (payload.containsKey("date")) {
	                Date date = dateFormat.parse((String) payload.get("date"));
	                existingDepense.setDate(date);
	            }
	            
	          	            	// Now, save the updated player information
	            Depense updatedDepense = depenseService.UpdateDepense(existingDepense);

	            return ResponseEntity.ok(updatedDepense);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
	        }
	    }




}
