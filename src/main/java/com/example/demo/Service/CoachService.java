package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Coach;



public interface CoachService {
	Coach saveCoach(Coach coach);
	Coach UpdateCoach(Long id, Coach coachDetails);
	void deleteCoach(Coach coach);
	void deleteCoachById(Long idcoach);
	Coach getCoach(Long idcoach);
	List<Coach> getAllCoachs();
	 List<Coach> searchCoachByName(String nameCoach);
}
