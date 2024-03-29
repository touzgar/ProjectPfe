package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Manager;
import com.example.demo.Model.Sponsor;
import com.example.demo.Service.SponsorService;

@RestController
@RequestMapping("/api/sponsor")
@CrossOrigin("*")
public class SponsorController {
@Autowired
SponsorService sponsorService;

@GetMapping("/getAll")
List<Sponsor> getAllSponsors(){
	return sponsorService.getAllSponsors();
}
 @GetMapping("/get/{id}")
public Sponsor getSponsorById(@PathVariable("id") Long id) {
	return sponsorService.getSponsor(id);
}
 @PostMapping("/add")
 
 public Sponsor createSponsor(@RequestBody Sponsor sponsor) {
     return sponsorService.saveSponsor(sponsor);
 }

 @PutMapping("/update/{id}")
public Sponsor updateSponsor(@RequestBody Sponsor sponsor) {
    return sponsorService.UpdateSponsor(sponsor);
}
    @DeleteMapping("/delete/{id}")
public void deleteSponsor(@PathVariable("id") Long id) {
    	sponsorService.deleteSponsorById(id);
}

}
