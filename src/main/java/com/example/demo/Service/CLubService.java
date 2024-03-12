package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Club;

public interface CLubService {
	Club saveClub(Club club);
	Club UpdateClub(Club club);
	void deleteClub(Club club);
	void deleteClubById(Long idClub);
	Club getClub(Long idClub);
	List<Club> getAllClubs();
	List<Club> searchByClubName(String clubName);

}