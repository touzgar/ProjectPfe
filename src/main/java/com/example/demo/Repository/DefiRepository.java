package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Club;
import com.example.demo.Model.Defi;

public interface DefiRepository extends JpaRepository<Defi, Long> {
	List<Defi> findByDateStartBefore(LocalDateTime now);
    List<Defi> findByDateStartAfter(LocalDateTime now);
    List<Defi> findByMatchNameContainingIgnoreCase(String matchName);
}
