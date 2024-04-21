package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

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
    if (team.getClub() != null) {
        Club club = team.getClub();
        club.getTeams().remove(team); // Remove the team from the club's list of teams
        clubRepository.save(club); // Save the club back to update its state
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
public Team saveTeamWithClubName(Team team, String clubName) {
    return clubRepository.findByClubName(clubName).map(club -> {
        team.setClub(club);
        return teamRepository.save(team);
    }).orElseThrow(() -> new RuntimeException("Club with name '" + clubName + "' not found"));
}
/*@Override
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
*/
@Override
public Team addCoachToTeam(String teamName, String coachName) {
    Team team = teamRepository.findByTeamName(teamName)
                    .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

    Coach coach = coachRepository.findByNameCoach(coachName)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Coach with name '" + coachName + "' not found"));

    if (!team.getClub().equals(coach.getClub())) {
        throw new RuntimeException("Coach and team do not belong to the same club");
    }

    team.getCoaches().add(coach);
    coach.getTeams().add(team);

    teamRepository.save(team);
    coachRepository.save(coach);

    return team;
}
@Override
public void removeCoachFromTeam(String teamName, String coachName) {
    Team team = teamRepository.findByTeamName(teamName)
            .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));

    Coach coach = coachRepository.findByNameCoach(coachName)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Coach with name '" + coachName + "' not found"));

    if (!team.getClub().equals(coach.getClub())) {
        throw new RuntimeException("Coach and team do not belong to the same club");
    }

    team.getCoaches().remove(coach);
    coach.getTeams().remove(team);

    teamRepository.save(team);
    coachRepository.save(coach);
}
@Override
public Team addPlayersToTeamByNames(String teamName, List<String> playerNames) {
    Team team = teamRepository.findByTeamName(teamName)
        .orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));
    
    List<Player> playersToAdd = new ArrayList<>();
    for (String playerName : playerNames) {
        Player player = playerRepository.findFirstByLeagalefullnameIgnoreCase(playerName)
            .orElseThrow(() -> new RuntimeException("Player with name '" + playerName + "' not found"));
        playersToAdd.add(player);
    }

    // Assuming a method to set the list of players directly doesn't exist, each player is added individually.
    for (Player player : playersToAdd) {
        player.setTeam(team); // Set the team for each player
        playerRepository.save(player); // Update each player in the database
    }
    
    return team; // Return the updated team with its new players
}
@Override
public Team removePlayersFromTeamByNames(String teamName, List<String> playerNames) {
    Team team = teamRepository.findByTeamName(teamName)
        .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

    playerNames.forEach(playerName -> {
        Player player = playerRepository.findFirstByLeagalefullnameIgnoreCase(playerName)
            .orElseThrow(() -> new RuntimeException("Player not found with name: " + playerName));
        if(player.getTeam().equals(team)) {
            player.setTeam(null); // Remove the association with the team
            playerRepository.save(player); // Save the player with the updated team association
        }
    });

    return teamRepository.save(team); // Save and return the updated team
}
@Override
public Team getTeamByName(String teamName) {
    return teamRepository.findByTeamName(teamName).orElse(null);
}
@Override
public List<Player> getPlayersByTeamNames(List<String> teamNames) {
    return playerRepository.findByTeamNames(teamNames);
}

}