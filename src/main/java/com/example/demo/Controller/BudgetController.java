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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Budget;
import com.example.demo.Model.Club;
import com.example.demo.Service.BudgetService;


@RestController
@RequestMapping("/api/budget")
@CrossOrigin("*")
public class BudgetController {
	@Autowired
	BudgetService budgetService;
	 @GetMapping("/getAll")
	List<Budget> getAllBudgets(){
		return budgetService.getAllBudgets();
	}
	 @GetMapping("/get/{id}")
	public Budget getBudgetById(@PathVariable("id") Long id) {
		return budgetService.getBudget(id);
	}
	    @DeleteMapping("/delete/{id}")
	public void deleteBudget(@PathVariable("id") Long id) {
	    	budgetService.deleteBudgetById(id);
	}
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Budget> searchBudgets(@RequestParam("name") String budgetName) {
	        return budgetService.searchByBudgetName(budgetName);
	    }
	 @PostMapping("/add")
	 public ResponseEntity<?> createBudget(@RequestBody Map<String, Object> payload) {
	     try {
	         
	         String description = (String) payload.get("description");
	         String budgetName = (String) payload.get("budgetName");
	         Double montantTotal = ((Number) payload.get("montantTotal")).doubleValue();
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date date = dateFormat.parse((String) payload.get("date"));
	           
	        

	          Budget budget = new Budget();
	          budget.setBudgetName (budgetName);
	          budget.setDescription(description);
	          budget.setDate(date);
	          budget.setMontantTotal(montantTotal);
	         // Use the service to handle the logic of adding club and coach by name
	          Budget savedBudget = budgetService.saveBudget(budget);
	         return ResponseEntity.ok(savedBudget);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<?> updateBudget(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
	        try {
	        	Budget existingBudget = budgetService.getBudget(id);
	            if (existingBudget == null) {
	                return ResponseEntity.notFound().build();
	            }

	            // Update player details from payload
	            String budgetName = (String) payload.get("budgetName");
	            if (budgetName != null) existingBudget.setBudgetName(budgetName);
	            
	            String description = (String) payload.get("description");
	            if (description != null) existingBudget.setDescription(description);
	            
	            if (payload.containsKey("salary")) {
	                Double montantTotal = ((Number) payload.get("montantTotal")).doubleValue();
	                existingBudget.setMontantTotal(montantTotal);
	            }

	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            if (payload.containsKey("date")) {
	                Date date = dateFormat.parse((String) payload.get("date"));
	                existingBudget.setDate(date);
	            }
	            
	          	            	// Now, save the updated player information
	            Budget updatedBudget = budgetService.UpdateBudget(existingBudget);

	            return ResponseEntity.ok(updatedBudget);
	        } catch (ParseException e) {
	            return ResponseEntity.badRequest().body("An error occurred parsing date fields: " + e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while updating the team: " + e.getMessage());
	        }
	    }

	 @GetMapping("/calculateBudgetByMonth")
	    public ResponseEntity<?> calculateBudgetByMonth() {
	        try {
	            Map<String, Double> budgetByMonth = budgetService.calculateBudgetByMonth();
	            return ResponseEntity.ok(budgetByMonth);
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("An error occurred while calculating budget by month: " + e.getMessage());
	        }
	    }
}
