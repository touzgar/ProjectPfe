package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Sponsor;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
	List<Sponsor> findBySponsorNameContainingIgnoreCase(String sponsorName);
}
