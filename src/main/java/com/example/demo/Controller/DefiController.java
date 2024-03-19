package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Defi;
import com.example.demo.Model.Tournament;
import com.example.demo.Service.DefiService;

@RestController
@RequestMapping("/api/defi")
@CrossOrigin("*")
public class DefiController {
	@Autowired
	DefiService defiService;
	
	@GetMapping("/getAll")
	List<Defi> getAllDefis(){
		return defiService.getAllDefis();
	}
	 @GetMapping("/get/{id}")
	public Defi getDefiById(@PathVariable("id") Long id) {
		return defiService.getDefi(id);
	}
	 @PutMapping("/update/{idDefi}")
	 public ResponseEntity<?> updateDefi(@PathVariable Long idDefi, @RequestBody Map<String, Object> updateInfo) {
	     try {
	         String matchName = (String) updateInfo.get("matchName");
	         LocalDateTime dateStart = LocalDateTime.parse((String) updateInfo.get("dateStart"), DateTimeFormatter.ISO_DATE_TIME);
	         String result = (String) updateInfo.get("result");

	         Defi updatedDefi = defiService.updateDefi(idDefi, matchName, dateStart, result);
	         return ResponseEntity.ok(updatedDefi);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error updating Defi: " + e.getMessage());
	     }
	 }
	 @GetMapping("/historical")
	    public ResponseEntity<List<Defi>> getHistoricalMatches() {
	        List<Defi> historicalMatches = defiService.getHistoricalMatches();
	        return ResponseEntity.ok(historicalMatches);
	    }
	 
}
