package com.example.demo.Service;
import java.util.List;

import com.example.demo.Model.Player;
import com.example.demo.Model.SessionTraining;

public interface SessionTrainingService {
	SessionTraining saveSessionTraining(SessionTraining sessionTraining);
	SessionTraining updateSessionTraining(Long id, String coachName, List<String> playerNames, SessionTraining sessionTrainingDetails);
	void deleteSessionTraining(SessionTraining sessionTraining);
	void deleteSessionTrainingById(Long idSessionTraining);
	SessionTraining getSessionTraining(Long idSessionTraining);
	List<SessionTraining> getAllSessionTrainings();
	List<SessionTraining> searchBySessionName(String sessionName);
	SessionTraining createSessionTraining(String coachName, List<String> playerNames, SessionTraining sessionTraining);
	List<Player> getPlayersBySessionName(String sessionName);
	SessionTraining removePlayersFromSessionByName(String sessionName, List<String> playerNames);

	 }
