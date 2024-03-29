package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Tournament;
import com.example.demo.Repository.TournamentRepository;

@Service
public class ScheduleTaskService {
	 @Autowired
	    private TournamentRepository tournamentRepository;

	 @Scheduled(cron = "0 * * * * ?") // Runs every minute
	    public void updateTournamentStatuses() {
	        List<Tournament> tournaments = tournamentRepository.findAll();
	        Date now = new Date(); // Use java.util.Date for current time
	        for (Tournament tournament : tournaments) {
	            // Check if the tournament's start date is before the current time and if its status is false
	            if (!tournament.getStatus() && tournament.getDateStart().before(now)) {
	                tournament.setStatus(true); // Set tournament status to true (active)
	                tournamentRepository.save(tournament); // Save the updated tournament
	                
	                // Log status change for debugging
	                System.out.println("Tournament status updated to active: " + tournament.getTournamentName());
	            }
	        }
	    }
	    }