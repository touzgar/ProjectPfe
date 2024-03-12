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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Coach;
import com.example.demo.Service.CoachService;

@RestController
@RequestMapping("/api/coach")
@CrossOrigin("*")
public class CoachController {
@Autowired
CoachService coachService;

@PostMapping("/add")
public Coach addCoach(@RequestBody Coach coach) {
    // Log the incoming coach details
    System.out.println("Received coach with club name: " + coach.getClubName());
    return coachService.saveCoach(coach);
}

@GetMapping("/getAll")
public List<Coach> getAllCoachs() {
    return coachService.getAllCoachs();
}
@GetMapping("/get/{id}")
public ResponseEntity<Coach> getCoachById(@PathVariable Long id) {
   Coach found = coachService.getCoach(id);
    return found != null ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
}
@PutMapping("/update/{id}")
public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach coachDetails) {
    Coach updatedCoach = coachService.UpdateCoach(id, coachDetails);
    return ResponseEntity.ok(updatedCoach);
}


@DeleteMapping("/delete/{id}")
public ResponseEntity<Void> deleteCoach(@PathVariable Long id) {
    coachService.deleteCoachById(id);
    return ResponseEntity.ok().build();
}
@GetMapping("/searchByName/{name}")
public ResponseEntity<List<Coach>> searchCoachByName(@PathVariable String name) {
    List<Coach> coaches = coachService.searchCoachByName(name);
    return !coaches.isEmpty() ? ResponseEntity.ok(coaches) : ResponseEntity.notFound().build();
}
}
