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
import com.example.demo.Model.Revenus;
import com.example.demo.Service.RevenusService;

@RestController
@RequestMapping("/api/revenus")
@CrossOrigin("*")
public class RevenusController {
@Autowired
RevenusService revenusService;

@GetMapping("/getAll")
List<Revenus> getAllRevenuss(){
	return revenusService.getAllRevenuss();
}
 @GetMapping("/get/{id}")
public Revenus getRevenusById(@PathVariable("id") Long id) {
	return revenusService.getRevenus(id);
}
    @DeleteMapping("/delete/{id}")
public void deleteRevenus(@PathVariable("id") Long id) {
    	revenusService.deleteRevenusById(id);
}
 
 @PostMapping("/add")
 public ResponseEntity<?> createRevenus(@RequestBody Map<String, Object> payload) {
     try {
         
         String description = (String) payload.get("description");
         String revenusName = (String) payload.get("revenus");
         Double montant = ((Number) payload.get("montant")).doubleValue();
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          Date date = dateFormat.parse((String) payload.get("date"));
           
        

          Revenus revenus = new Revenus();
          revenus.setRevenusName (revenusName);
          revenus.setDescription(description);
          revenus.setDate(date);
          revenus.setMontant(montant);
         // Use the service to handle the logic of adding club and coach by name
          Revenus savedRevenus = revenusService.saveRevenus(revenus);
         return ResponseEntity.ok(savedRevenus);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
     }
 }
 
 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateRevenus(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
        try {
        	Revenus existingRevenus = revenusService.getRevenus(id);
            if (existingRevenus == null) {
                return ResponseEntity.notFound().build();
            }

            // Update player details from payload
            String revenusName = (String) payload.get("revenusName");
            if (revenusName != null) existingRevenus.setRevenusName(revenusName);
            
            String description = (String) payload.get("description");
            if (description != null) existingRevenus.setDescription(description);
            
            if (payload.containsKey("salary")) {
                Double montant = ((Number) payload.get("montant")).doubleValue();
                existingRevenus.setMontant(montant);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (payload.containsKey("date")) {
                Date date = dateFormat.parse((String) payload.get("date"));
                existingRevenus.setDate(date);
            }
            
          	            	// Now, save the updated player information
            Revenus updatedRevenus = revenusService.UpdateRevenus(existingRevenus);

            return ResponseEntity.ok(updatedRevenus);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
        }
    }
 @GetMapping("/total")
 public ResponseEntity<Double> getTotalRevenus() {
     Double totalRevenus = revenusService.calculateTotalRevenus();
     return ResponseEntity.ok(totalRevenus);
 }
 @GetMapping("/totalByMonth")
 public ResponseEntity<Map<String, Double>> getTotalRevenusByMonth() {
     Map<String, Double> totalRevenusByMonth = revenusService.calculateTotalRevenusByMonth();
     return ResponseEntity.ok(totalRevenusByMonth);
 }








}
