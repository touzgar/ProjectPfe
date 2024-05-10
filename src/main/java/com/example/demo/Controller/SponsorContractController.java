package com.example.demo.Controller;

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


import com.example.demo.Model.SponsorContract;

import com.example.demo.Service.SponsorContractService;

@RestController
@RequestMapping("/api/sponsorContract")
@CrossOrigin("*")
public class SponsorContractController {
@Autowired
SponsorContractService sponsorContractService;

@GetMapping("/getAll")
List<SponsorContract> getAllSponsorsContract(){
	return sponsorContractService.getAllSponsorContract();
}
 @GetMapping("/get/{id}")
public SponsorContract getSponsorContractById(@PathVariable("id") Long id) {
	return sponsorContractService.getSponsor(id);
}
 @PostMapping("/add")
 
 public SponsorContract createSponsor(@RequestBody SponsorContract sponsorContract) {
     return sponsorContractService.saveSponsorContract(sponsorContract);
 }

 @PutMapping("/update/{id}")
 public ResponseEntity<?> updateSponsorContractWithDetails(@PathVariable("id") Long id, @RequestBody Map<String, Object> payload) {
     try {
         String sponsorContractName = (String) payload.get("sponsorContractName");
         String sponsorUsername = (String) payload.get("sponsorUsername");
         String teamName = (String) payload.get("teamName");
         String objectif = (String) payload.get("objectif");

         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
         Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));

         SponsorContract sponsorContract = new SponsorContract();
         sponsorContract.setIdSponsorContract(id);
         sponsorContract.setSponsorContractName(sponsorContractName);
         sponsorContract.setDateStart(dateStart);
         sponsorContract.setDateEnd(dateEnd);
         sponsorContract.setObjectif(objectif);

         SponsorContract updatedSponsorContract = sponsorContractService.updateSponsorContractWithDetails(
                 sponsorContract, sponsorUsername, teamName);

         return ResponseEntity.ok(updatedSponsorContract);
     } catch (Exception e) {
         return ResponseEntity.badRequest().body("Error updating SponsorContract: " + e.getMessage());
     }
 }

    @DeleteMapping("/delete/{id}")
public void deleteSponsorContract(@PathVariable("id") Long id) {
    	sponsorContractService.deleteSponsorContractById(id);
}
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<SponsorContract> searchSponsorContracts(@RequestParam("name") String sponsorContractName) {
        return sponsorContractService.searchBySponsorContractName(sponsorContractName);
    }
  /*  @PostMapping("/addSponsorToSponsorContract")
    public ResponseEntity<?> addSponsorToSPonsorContract(@RequestParam("sponsorContractName") String sponsorContractName, 
                                            @RequestParam("sponsorName") String sponsorName) {
        try {
            SponsorContract updatedSponsorContract = sponsorContractService.addSponsorToSponsorContract(sponsorContractName, sponsorName);
            return ResponseEntity.ok(updatedSponsorContract);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while adding coach to team: " + e.getMessage());
        }
    }
*/
    @PostMapping("/addSponsorContractWithDetails")
    public ResponseEntity<?> addSponsorContractWithDetails(@RequestBody Map<String, Object> payload) {
        try {
            String sponsorContractName = (String) payload.get("sponsorContractName");
            String sponsorUsername = (String) payload.get("sponsorUsername");
            String teamName = (String) payload.get("teamName");
            String objectif = (String) payload.get("objectif");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateStart = dateFormat.parse((String) payload.get("dateStart"));
            Date dateEnd = dateFormat.parse((String) payload.get("dateEnd"));

            SponsorContract sponsorContract = new SponsorContract();
            sponsorContract.setSponsorContractName(sponsorContractName);
            sponsorContract.setDateStart(dateStart);
            sponsorContract.setDateEnd(dateEnd);
            sponsorContract.setObjectif(objectif);

            SponsorContract updatedSponsorContract = sponsorContractService.addSponsorContractWithDetails(
                    sponsorContract, sponsorUsername, teamName);

            return ResponseEntity.ok(updatedSponsorContract);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating SponsorContract: " + e.getMessage());
        }
    }
}
