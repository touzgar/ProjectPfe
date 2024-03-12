package com.example.demo.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {
	List<Coach> findByNameCoach(String nameCoach);
}
