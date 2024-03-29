package com.example.demo.Controller;

import java.util.List;

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

import com.example.demo.Model.Club;
import com.example.demo.Model.SponsorContract;
import com.example.demo.Model.Team;
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
public SponsorContract updateSponsor(@RequestBody SponsorContract sponsorContract) {
    return sponsorContractService.UpdateSponsorContract(sponsorContract);
}
    @DeleteMapping("/delete/{id}")
public void deleteSponsorContract(@PathVariable("id") Long id) {
    	sponsorContractService.deleteSponsorContractById(id);
}
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<SponsorContract> searchSponsorContracts(@RequestParam("name") String sponsorContractName) {
        return sponsorContractService.searchBySponsorContractName(sponsorContractName);
    }
    @PostMapping("/addSponsorToSponsorContract")
    public ResponseEntity<?> addSponsorToSPonsorContract(@RequestParam("sponsorContractName") String sponsorContractName, 
                                            @RequestParam("sponsorName") String sponsorName) {
        try {
            SponsorContract updatedSponsorContract = sponsorContractService.addSponsorToSponsorContract(sponsorContractName, sponsorName);
            return ResponseEntity.ok(updatedSponsorContract);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred while adding coach to team: " + e.getMessage());
        }
    }

}
