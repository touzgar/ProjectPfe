package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {
	
    List<Club> findByClubNameContainingIgnoreCase(String clubName);
    Optional<Club> findByClubName(String clubName);//create this bech najem na3ti ism club kif nisna3 team
}