package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Coach;
import com.example.demo.Model.Player;
import com.example.demo.Model.SessionTraining;
import com.example.demo.Repository.CoachRepository;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.SessionTrainingRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SessionTrainingServiceImp implements SessionTrainingService {
	@Autowired
	SessionTrainingRepository sessionTrainingRepository; 
	@Autowired
	CoachRepository coachRepository;
	@Autowired
	PlayerRepository playerRepository;
		@Override
		public SessionTraining saveSessionTraining(SessionTraining sessionTraining) {
				return sessionTrainingRepository.save(sessionTraining);
		}

		@Override
		public void deleteSessionTraining(SessionTraining sessionTraining) {
			sessionTrainingRepository.delete(sessionTraining);
			
		}

		@Override
		public void deleteSessionTrainingById(Long idSessionTraining) {
			sessionTrainingRepository.deleteById(idSessionTraining);
			
		}

		@Override
		public SessionTraining getSessionTraining(Long idSessionTraining) {
			
			return sessionTrainingRepository.findById(idSessionTraining).get();
		}

		@Override
		public List<SessionTraining> getAllSessionTrainings() {
			
			return sessionTrainingRepository.findAll();
		}

		@Override
		public List<SessionTraining> searchBySessionName(String sessionName) {
			
			return sessionTrainingRepository.findBysessionNameContainingIgnoreCase(sessionName);
		}


		  @Override
		    @Transactional
		    public SessionTraining updateSessionTraining(Long id, String coachName, List<String> playerNames, SessionTraining sessionTrainingDetails) {
		        SessionTraining sessionTraining = sessionTrainingRepository.findById(id)
		                .orElseThrow(() -> new EntityNotFoundException("SessionTraining not found with id: " + id));
		        
		        sessionTraining.setSessionName(sessionTrainingDetails.getSessionName());
		        sessionTraining.setDateStart(sessionTrainingDetails.getDateStart());
		        sessionTraining.setDateEnd(sessionTrainingDetails.getDateEnd());
		        sessionTraining.setObjectifs(sessionTrainingDetails.getObjectifs());
		        sessionTraining.setFeedbacksEntraineurs(sessionTrainingDetails.getFeedbacksEntraineurs());

		        Coach coach = coachRepository.findByNameCoachIgnoreCase(coachName)
		            .orElseThrow(() -> new EntityNotFoundException("Coach not found"));
		        sessionTraining.setCoach(coach);

		        List<Player> players = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
		        sessionTraining.setPresencePlayer(players);

		        return sessionTrainingRepository.save(sessionTraining);
		    }
		  @Override
		    
		    @Transactional
		    public SessionTraining createSessionTraining(String coachName, List<String> playerNames, SessionTraining sessionTraining) {
		        // Fetch coaches by name
		        List<Coach> coaches = coachRepository.findByNameCoach(coachName);
		        if (coaches.isEmpty()) {
		            throw new EntityNotFoundException("Coach not found with name: " + coachName);
		        } else if (coaches.size() > 1) {
		            throw new IllegalStateException("Multiple coaches found with name: " + coachName);
		        }
		        Coach coach = coaches.get(0); // Safely get the first (and supposedly only) coach

		        // Fetch players by names
		        List<Player> players = new ArrayList<>();
		        for (String playerName : playerNames) {
		            List<Player> foundPlayers = playerRepository.findByLeagalefullnameInIgnoreCase(List.of(playerName));
		            if (foundPlayers.isEmpty()) {
		                throw new EntityNotFoundException("Player not found with name: " + playerName);
		            } else if (foundPlayers.size() > 1) {
		                throw new IllegalStateException("Multiple players found with name: " + playerName);
		            }
		            players.add(foundPlayers.get(0)); // Safely add the found player
		        }

		        // Assuming sessionTraining is properly constructed
		        sessionTraining.setCoach(coach);
		        sessionTraining.setPresencePlayer(players);

		        return sessionTrainingRepository.save(sessionTraining);
		    }
		  
		  
		  
		  
		  @Override
		    public List<Player> getPlayersBySessionName(String sessionName) {
		        SessionTraining sessionTraining = sessionTrainingRepository.findBySessionName(sessionName)
		                .orElseThrow(() -> new EntityNotFoundException("Session not found with name: " + sessionName));
		        return sessionTraining.getPresencePlayer();
		    }
		   @Override
		    @Transactional
		    public SessionTraining removePlayersFromSessionByName(String sessionName, List<String> playerNames) {
		        SessionTraining sessionTraining = sessionTrainingRepository.findBySessionName(sessionName)
		                .orElseThrow(() -> new EntityNotFoundException("SessionTraining not found with name: " + sessionName));
		        
		        List<Player> playersToRemove = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
		        if (playersToRemove.isEmpty()) {
		            throw new EntityNotFoundException("Some or all players not found");
		        }

		        sessionTraining.getPresencePlayer().removeAll(playersToRemove);
		        return sessionTrainingRepository.save(sessionTraining);
		    }

		
		   }
