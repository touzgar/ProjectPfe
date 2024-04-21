package com.example.demo.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Depense;
import com.example.demo.Repository.DepenseRepository;

@Service
public class DepenseServiceImpl implements DepenseService {
@Autowired
DepenseRepository depenseRepository;
	@Override
	public Depense saveDepense(Depense depense) {
		
		return depenseRepository.save(depense);
	}

	@Override
	public Depense UpdateDepense(Depense depense) {

		return depenseRepository.save(depense);
	}

	@Override
	public void deleteDepense(Depense depense) {
		depenseRepository.delete(depense);
		
	}

	@Override
	public void deleteDepenseById(Long idDepense) {
		depenseRepository.deleteById(idDepense);
		
	}

	@Override
	public Depense getDepense(Long idDepense) {
		
		return depenseRepository.findById(idDepense).get();
	}

	@Override
	public List<Depense> getAllDepenses() {
		
		return depenseRepository.findAll();
	}
	 @Override
	    public Double calculateTotalDepense() {
	        List<Depense> depenses = depenseRepository.findAll();
	        Double total = 0.0;
	        for (Depense depense : depenses) {
	            total += depense.getMontant();
	        }
	        return total;
	}
	 @Override
	    public Map<String, Double> calculateTotalDepenseByMonth() {
	        List<Depense> depenses = depenseRepository.findAll();
	        Map<String, Double> totalDepenseByMonth = new HashMap<>();
	        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

	        for (Depense depense : depenses) {
	            LocalDate depenseDate = depense.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            String monthYearKey = monthYearFormatter.format(depenseDate);
	            Double total = totalDepenseByMonth.getOrDefault(monthYearKey, 0.0);
	            total += depense.getMontant();
	            totalDepenseByMonth.put(monthYearKey, total);
	        }

	        return totalDepenseByMonth;
	    }
	 
}
