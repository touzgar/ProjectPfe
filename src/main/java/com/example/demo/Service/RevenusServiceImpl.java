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
import com.example.demo.Model.Revenus;
import com.example.demo.Repository.RevenusRepository;

@Service
public class RevenusServiceImpl implements RevenusService  {
@Autowired
RevenusRepository revenusRepository; 
	@Override
	public Revenus saveRevenus(Revenus revenus) {
		
		return revenusRepository.save(revenus);
	}

	@Override
	public Revenus UpdateRevenus(Revenus revenus) {
		return revenusRepository.save(revenus);	}

	@Override
	public void deleteRevenus(Revenus revenus) {
		revenusRepository.delete(revenus);
	}

	@Override
	public void deleteRevenusById(Long idRevenus) {
		revenusRepository.deleteById(idRevenus);
		
	}

	@Override
	public Revenus getRevenus(Long idRevenus) {
		
		return revenusRepository.findById(idRevenus).get();
	}

	@Override
	public List<Revenus> getAllRevenuss() {
		
		return revenusRepository.findAll();
	}
	 @Override
	    public Double calculateTotalRevenus() {
	        List<Revenus> revenus = revenusRepository.findAll();
	        Double total = 0.0;
	        for (Revenus revenu : revenus) {
	            total += revenu.getMontant();
	        }
	        return total;
	}
	 @Override
	    public Map<String, Double> calculateTotalRevenusByMonth() {
	        List<Revenus> revenus = revenusRepository.findAll();
	        Map<String, Double> totalRevenusByMonth = new HashMap<>();
	        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

	        for (Revenus revenu : revenus) {
	            LocalDate depenseDate = revenu.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            String monthYearKey = monthYearFormatter.format(depenseDate);
	            Double total = totalRevenusByMonth.getOrDefault(monthYearKey, 0.0);
	            total += revenu.getMontant();
	            totalRevenusByMonth.put(monthYearKey, total);
	        }

	        return totalRevenusByMonth;
	    }



}
