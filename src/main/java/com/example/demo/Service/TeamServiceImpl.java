package com.example.demo.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Club;
import com.example.demo.Model.Coach;
import com.example.demo.Model.Player;
import com.example.demo.Model.Team;
import com.example.demo.Repository.ClubRepository;
import com.example.demo.Repository.CoachRepository;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.TeamRepository;

import jakarta.transaction.Transactional;

@Service
public class TeamServiceImpl implements TeamService {
@Autowired
TeamRepository teamRepository;
@Autowired
ClubRepository clubRepository;
@Autowired
PlayerRepository playerRepository;
@Autowired
CoachRepository coachRepository;
@Override
public Team saveTeam(Team team) {
	
	return teamRepository.save(team);
}

@Override
public Team UpdateTeam(Team team) {
	return teamRepository.save(team);
}

@Override
public void deleteTeam(Team team) {
	teamRepository.delete(team);
	
}

@Override
@Transactional
public void deleteTeamById(Long idTeam) {
    Team team = teamRepository.findById(idTeam).orElseThrow(() -> new RuntimeException("Team not found for this id :: " + idTeam));
    
    // Disassociate team from its club, if any
    Club club = team.getClub();
    if (club != null) {
        club.setTeam(null);
        clubRepository.save(club);
    }

    // Disassociate all players from the team without deleting them
    List<Player> players = team.getPlayers();
    if (players != null) {
        for (Player player : players) {
            player.setTeam(null); // Set the team of each player to null
            // Note: Assuming you have a playerRepository or similar to save the player
            playerRepository.save(player); // Save the player to update its team reference in the database
        }
    }

    // Delete the team
    teamRepository.delete(team);
}


@Override
public Team getTeam(Long idClub) {
	
	return teamRepository.findById(idClub).get();
}

@Override
public List<Team> getAllTeams() {
	return teamRepository.findAll();
}
@Override
public List<Team> searchByTeamName(String teamName) {
    return teamRepository.findByTeamNameContainingIgnoreCase(teamName);
}
@Override
public List<String> findParticipatingTournamentsByTeamName(String teamName) {
    Optional<Team> team = teamRepository.findByTeamName(teamName);
    return team.map(Team::getParticipatingTournaments).orElse(Collections.emptyList());
}
@Override
public Team saveTeamWithClubName(Team team, String clubName) {
    return clubRepository.findByClubName(clubName).map(club -> {
        team.setClub(club);
        return teamRepository.save(team);
    }).orElseThrow(() -> new RuntimeException("Club with name '" + clubName + "' not found"));
}
@Override
@Transactional
public Team saveTeamWithClubAndCoachName(Team team, String clubName, String coachName) {
    Club club = clubRepository.findByClubName(clubName)
        .orElseThrow(() -> new RuntimeException("Club with name '" + clubName + "' not found"));
    team.setClub(club);

    Coach coach = coachRepository.findByNameCoach(coachName)
        .stream()
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Coach not found with name: " + coachName));
    team.setCoach(coach);

    return teamRepository.save(team);
}

}