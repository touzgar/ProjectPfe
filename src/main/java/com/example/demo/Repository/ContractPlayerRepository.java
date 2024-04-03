package com.example.demo.Repository;

import com.example.demo.Model.ContractPlayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ContractPlayerRepository extends JpaRepository<ContractPlayer, Long> {
	
	List<ContractPlayer> findByPlayer_LeagalefullnameIgnoreCase(String playerName);

}
