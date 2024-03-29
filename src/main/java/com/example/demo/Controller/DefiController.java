package com.example.demo.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Club;
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
	            // Parsing the date from String to Date
	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	            Date dateStart = formatter.parse((String) updateInfo.get("dateStart"));
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
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Defi> searchMatchs(@RequestParam("name") String matchName) {
	        return defiService.searchByMatchName(matchName);
	    }

	 
}
