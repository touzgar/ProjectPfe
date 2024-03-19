package com.example.demo.Service;

import java.time.LocalDateTime;

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
	        LocalDateTime now = LocalDateTime.now();
	        for (Tournament tournament : tournaments) {
	            if (!tournament.getStatus() && now.isAfter(tournament.getDateStart())) {
	                tournament.setStatus(true);
	                tournamentRepository.save(tournament);
	                // Log status change for debugging
	                System.out.println("Tournament status updated: " + tournament.getTournamentName());
	            }
	        }
	    }
	    }