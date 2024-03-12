package com.example.demo.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Player;


public interface PlayerRepository extends JpaRepository<Player, Long> {
	  Optional<Player> findFirstByLeagalefullnameIgnoreCase(String leagalefullname);
}