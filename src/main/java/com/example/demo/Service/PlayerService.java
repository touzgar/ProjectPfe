package com.example.demo.Service;

import java.util.List;
import java.util.Optional;


import com.example.demo.Model.Player;


public interface PlayerService {
    Player savePlayer(Player player);
    Player updatePlayer(Player player );
    void deletePlayer(Player player);
    void deletePlayerById(Long idPlayer);
    Player getPlayer(Long idPlayer);
    List<Player> getAllPlayers();
    Optional<Player> findPlayerByName(String leagalefullname);
    Player savePlayerWithTeamName(Player player, String teamName);
    }
    
    

