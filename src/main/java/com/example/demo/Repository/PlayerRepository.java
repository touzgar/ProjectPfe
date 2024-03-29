package com.example.demo.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Player;


public interface PlayerRepository extends JpaRepository<Player, Long> {
	  Optional<Player> findFirstByLeagalefullnameIgnoreCase(String leagalefullname);
	  List<Player> findByLeagalefullnameInIgnoreCase(List<String> leagalefullnames);
	  List<Player> findByLeagalefullname(String leagalefullnames);
}