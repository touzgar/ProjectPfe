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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Club;
import com.example.demo.Service.CLubService;

@RestController
@RequestMapping("/api/club")
@CrossOrigin("*")

public class CLubController {
	@Autowired
	CLubService clubService;
	 @GetMapping("/getAll")
	List<Club> getAllClubs(){
		return clubService.getAllClubs();
	}
	 @GetMapping("/get/{id}")
	public Club getClubById(@PathVariable("id") Long id) {
		return clubService.getClub(id);
	}
	 @PostMapping("/add")
	 public Club createClub(@RequestBody Club club) {
	     return clubService.saveClub(club);
	 }

	 @PutMapping("/update/{id}")
	public Club updateClub(@RequestBody Club club) {
	    return clubService.UpdateClub(club);
	}
	    @DeleteMapping("/delete/{id}")
	public void deleteclub(@PathVariable("id") Long id) {
		clubService.deleteClubById(id);
	}
	 @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public List<Club> searchClubs(@RequestParam("name") String clubName) {
	        return clubService.searchByClubName(clubName);
	    }
}