package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Club;
import com.example.demo.Model.Coach;
import com.example.demo.Repository.ClubRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClubSeriveImpl implements CLubService {
@Autowired
ClubRepository clubRepository;

@Override
public Club saveClub(Club club) {
	
	return clubRepository.save(club) ;

}

@Override
public Club UpdateClub(Club club) {
	return clubRepository.save(club) ;}

@Override
public void deleteClub(Club club) {
	clubRepository.delete(club);
	
}

@Override
@Transactional
public void deleteClubById(Long idClub) {
    Club club = clubRepository.findById(idClub)
            .orElseThrow(() -> new EntityNotFoundException("Club not found with id: " + idClub));

    // Iterate through each coach and set their club to null before deleting the club
    List<Coach> coaches = club.getCoach();
    if (coaches != null) {
        for (Coach coach : coaches) {
            coach.setClub(null);
            // If you have a coachRepository, save the coach here
            // e.g., coachRepository.save(coach);
        }
    }

    // Now, delete the club
    clubRepository.deleteById(idClub);
}


@Override
public Club getClub(Long idClub) {
	return clubRepository.findById(idClub).get();
	}

@Override
public List<Club> getAllClubs() {
	
	return clubRepository.findAll();
}

@Override
public List<Club> searchByClubName(String clubName) {
    return clubRepository.findByClubNameContainingIgnoreCase(clubName);
}
}
