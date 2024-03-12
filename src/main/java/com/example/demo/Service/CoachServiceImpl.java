package com.example.demo.Service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Club;
import com.example.demo.Model.Coach;
import com.example.demo.Repository.ClubRepository;
import com.example.demo.Repository.CoachRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CoachServiceImpl implements CoachService {
@Autowired
CoachRepository  coachRepository; 
@Autowired
ClubRepository clubRepository;
@Override
public Coach saveCoach(Coach coach) {
    // Check if the clubName is not null and not empty
    if (coach.getClubName() != null && !coach.getClubName().isEmpty()) {
        // Find the club by name
        Club club = clubRepository.findByClubName(coach.getClubName())
                .orElseThrow(() -> new EntityNotFoundException("Club not found with name: " + coach.getClubName()));
        coach.setClub(club); // Set the found club to the coach
    }
    // Save the coach with the club set
    return coachRepository.save(coach);
}

@Override
@Transactional
public Coach UpdateCoach(Long id, Coach coachDetails) {
    Coach coach = coachRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Coach not found with id: " + id));

    coach.setNameCoach(coachDetails.getNameCoach());
    coach.setEmail(coachDetails.getEmail());
    coach.setRapport(coachDetails.getRapport());

    if (coachDetails.getClubName() != null && !coachDetails.getClubName().isEmpty()) {
        Club club = clubRepository.findByClubName(coachDetails.getClubName())
                .orElseThrow(() -> new EntityNotFoundException("Club not found with name: " + coachDetails.getClubName()));
        coach.setClub(club);
    } else {
        // Optionally handle the removal of a club from a coach
        coach.setClub(null);
    }

    return coachRepository.save(coach);
}

	@Override
	public void deleteCoach(Coach coach) {
	coachRepository.delete(coach);
		
	}

	@Override
	public void deleteCoachById(Long idcoach) {
		coachRepository.deleteById(idcoach);
		
	}

	@Override
	public Coach getCoach(Long idcoach) {
		
		return coachRepository.findById(idcoach).get();
	}

	@Override
	public List<Coach> getAllCoachs() {
		
		return coachRepository.findAll();
	}
	
	  @Override
	    public List<Coach> searchCoachByName(String nameCoach) {
	        return coachRepository.findByNameCoach(nameCoach);
	    }
	

}
