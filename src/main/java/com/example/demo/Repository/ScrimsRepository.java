package com.example.demo.Repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Scrims;


public interface ScrimsRepository extends JpaRepository<Scrims, Long> {

	 List<Scrims> findBysessionNameContainingIgnoreCase(String sessionName);
	 Optional<Scrims> findBySessionName(String sessionName);

	 

}
