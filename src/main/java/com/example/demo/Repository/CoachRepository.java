package com.example.demo.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Model.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
	List<Coach> findByNameCoach(String nameCoach);
	 Optional<Coach> findByNameCoachIgnoreCase(String nameCoach);
	 

}
