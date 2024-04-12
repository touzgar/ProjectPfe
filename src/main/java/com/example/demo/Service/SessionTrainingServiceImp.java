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
import com.example.demo.Model.User;
import com.example.demo.Repository.CoachRepository;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.SessionTrainingRepository;
import com.example.demo.Repository.UserRepository;

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
	@Autowired
	UserRepository userRepository;
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
		  public SessionTraining updateSessionTraining(Long id, String username, List<String> playerNames, SessionTraining sessionTrainingDetails) {
		      SessionTraining sessionTraining = sessionTrainingRepository.findById(id)
		              .orElseThrow(() -> new EntityNotFoundException("SessionTraining not found with id: " + id));

		      User user = userRepository.searchByUsername(username)
		              .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
		      // Check if user has the role of 'coach'
		      boolean isCoach = user.getRoles().stream()
		          .anyMatch(role -> role.getRole().equalsIgnoreCase("coach"));
		      if (!isCoach) {
		          throw new IllegalArgumentException("User is not a coach");
		      }

		      sessionTraining.setUser(user); // Assuming you have changed SessionTraining to reference User instead of Coach
		      sessionTraining.setSessionName(sessionTrainingDetails.getSessionName());
		      sessionTraining.setDateStart(sessionTrainingDetails.getDateStart());
		      sessionTraining.setDateEnd(sessionTrainingDetails.getDateEnd());
		      sessionTraining.setObjectifs(sessionTrainingDetails.getObjectifs());
		      sessionTraining.setFeedbacksEntraineurs(sessionTrainingDetails.getFeedbacksEntraineurs());

		      List<Player> players = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
		      if (players.isEmpty()) {
		          throw new EntityNotFoundException("Players not found");
		      }
		      sessionTraining.setPresencePlayer(players);

		      return sessionTrainingRepository.save(sessionTraining);
		  }
		  @Override
		  @Transactional
		  public SessionTraining createSessionTraining(String username, List<String> playerNames, SessionTraining sessionTraining) {
		      User user = userRepository.searchByUsername(username)
		          .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

		      // Check if user has the role of 'coach'
		      boolean isCoach = user.getRoles().stream()
		          .anyMatch(role -> role.getRole().equalsIgnoreCase("coach"));
		      if (!isCoach) {
		          throw new IllegalArgumentException("User is not a coach");
		      }

		      List<Player> players = playerRepository.findByLeagalefullnameInIgnoreCase(playerNames);
		      if (players.isEmpty()) {
		          throw new EntityNotFoundException("Players not found");
		      }

		      sessionTraining.setUser(user);
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
