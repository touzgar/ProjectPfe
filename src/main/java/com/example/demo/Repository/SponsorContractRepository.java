package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.SponsorContract;


public interface SponsorContractRepository extends JpaRepository<SponsorContract, Long> {
    @Query("SELECT s FROM SponsorContract s WHERE LOWER(s.sponsorContractName) LIKE LOWER(:sponsorContractName)")
    List<SponsorContract> findBySponsorContractNameContainingIgnoreCase(@Param("sponsorContractName") String sponsorContractName);
    Optional<SponsorContract> findBySponsorContractName(String sponsorContractName);
}
