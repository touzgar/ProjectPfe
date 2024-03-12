package com.example.demo.Service;

import com.example.demo.Model.Player;
import com.example.demo.Model.Team;
import com.example.demo.Repository.PlayerRepository;
import com.example.demo.Repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    TeamRepository teamRepository;

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Player player) {
    	
        return playerRepository.save(player);
    }

    @Override
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    @Override
    public void deletePlayerById(Long idPlayer) {
        Player player = playerRepository.findById(idPlayer).orElseThrow(() -> new RuntimeException("Player not found for this id :: " + idPlayer));
        
        // Remove the association with the team
        if(player.getTeam() != null) {
            Team team = player.getTeam();
            team.getPlayers().remove(player); // If you maintain a list of players in Team
            player.setTeam(null); // This dissociates the player from the team
        }

        // Now delete the player
        playerRepository.delete(player);
    }


    @Override
    public Player getPlayer(Long idPlayer) {
        return playerRepository.findById(idPlayer).orElse(null);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    @Override
    public Optional<Player> findPlayerByName(String leagalefullname) {
        return playerRepository.findFirstByLeagalefullnameIgnoreCase(leagalefullname);
    }
   
    
      @Override
public Player savePlayerWithTeamName(Player player, String teamName) {
    return teamRepository.findByTeamName(teamName).map(team -> {
        player.setTeam(team);
        return playerRepository.save(player);
    }).orElseThrow(() -> new RuntimeException("Team with name '" + teamName + "' not found"));



     
      }
}