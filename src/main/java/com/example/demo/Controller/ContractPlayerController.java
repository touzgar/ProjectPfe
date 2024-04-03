package com.example.demo.Controller;

import com.example.demo.Model.AchievementPlayer;
import com.example.demo.Model.ContractPlayer;
import com.example.demo.Model.Team;
import com.example.demo.Service.ContractPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contractPlayer")
@CrossOrigin("*")
public class ContractPlayerController {

    @Autowired
    ContractPlayerService contractPlayerService;

    @GetMapping("/getAll")
    public List<ContractPlayer> getAllContractPlayers() {
        return contractPlayerService.getAllContractPlayers();
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ContractPlayer> getContractPlayerById(@PathVariable Long id) {
        ContractPlayer contractPlayer = contractPlayerService.getContractPlayer(id);
        return contractPlayer != null ? ResponseEntity.ok(contractPlayer) : ResponseEntity.notFound().build();
    }

   /* @PostMapping("/add")
    public ResponseEntity<ContractPlayer> createContractPlayer(@RequestBody ContractPlayer contractPlayer) {
        ContractPlayer newContractPlayer = contractPlayerService.createContractPlayer(contractPlayer);
        return ResponseEntity.ok(newContractPlayer);
    }*/
    @PostMapping("/add")
	 public ResponseEntity<?> createContractPlayer(@RequestBody Map<String, Object> payload) {
	     try {
	         String detailsContractuels = (String) payload.get("detailsContractuels");
	         String termesFinanciers = (String) payload.get("termesFinanciers");
	         String clausesSpecifiques=(String) payload.get("termesFinanciers");
	         String ContractName=(String) payload.get("ContractName");
	         String leagalefullname = (String) payload.get("leagalefullname");
	         List<String> objectifs = (List<String>) payload.get("objectifs");
	           
	        
	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	          Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
	          Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));
		         

	         ContractPlayer contractPlayer = new ContractPlayer();
	         contractPlayer.setDetailsContractuels(detailsContractuels);
	         contractPlayer.setClausesSpecifiques(clausesSpecifiques);
	         contractPlayer.setContractName(ContractName);
	         contractPlayer.setTermesFinanciers(termesFinanciers);
	         contractPlayer.setObjectifs(objectifs);
	         contractPlayer.setDateStart(dateStart);
	         contractPlayer.setDateEnd(dateEnd);
	         
	         // Use the service to handle the logic of adding club and coach by name
	         ContractPlayer savedContractPlayer = contractPlayerService.saveContractNameWithPlayerName(contractPlayer, leagalefullname);
	         return ResponseEntity.ok(savedContractPlayer);
	     } catch (Exception e) {
	         return ResponseEntity.badRequest().body("Error creating team: " + e.getMessage());
	     }
	 }

    
    
    
    
    
    
    
    
    
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ContractPlayer> updateContractPlayer(@PathVariable Long id, @RequestBody ContractPlayer contractPlayerDetails) {
        ContractPlayer existingContractPlayer = contractPlayerService.getContractPlayer(id);
        if (existingContractPlayer != null) {
            // Update fields from contractPlayerDetails to existingContractPlayer
            existingContractPlayer.setDetailsContractuels(contractPlayerDetails.getDetailsContractuels());
            existingContractPlayer.setTermesFinanciers(contractPlayerDetails.getTermesFinanciers());
            existingContractPlayer.setClausesSpecifiques(contractPlayerDetails.getClausesSpecifiques());
            existingContractPlayer.setObjectifs(contractPlayerDetails.getObjectifs());
            existingContractPlayer.setDateStart(contractPlayerDetails.getDateStart());
            existingContractPlayer.setDateEnd(contractPlayerDetails.getDateEnd());
            

            // Save the updated contractPlayer
            ContractPlayer updatedContractPlayer = contractPlayerService.updateContractPlayer(existingContractPlayer);
            return ResponseEntity.ok(updatedContractPlayer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
    
    @DeleteMapping("/delete/{id}")
 	public void deleteContractPlayer(@PathVariable("id") Long id) {
 		contractPlayerService.deleteContractPlayerById(id);
 	}
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<ContractPlayer> searchContractPlayer(@RequestParam("name") String playerName) {
        return contractPlayerService.searchByPlayerName(playerName);
    }
}