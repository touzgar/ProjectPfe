package com.example.demo.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Budget;
import com.example.demo.Model.Depense;
import com.example.demo.Model.Revenus;
import com.example.demo.Repository.BudgetRepository;

@Service
public class BudgetServiceImpl implements BudgetService {
@Autowired
BudgetRepository budgetRepository;
@Autowired
DepenseService depenseService;
@Autowired
RevenusService revenusService;
	@Override
	public Budget saveBudget(Budget budget) {
		
		return budgetRepository.save(budget);
	}

	@Override
	public Budget UpdateBudget(Budget budget) {
		
		 return budgetRepository.save(budget);
	}

	@Override
	public void deleteBudget(Budget budget) {
		budgetRepository.delete(budget);
	}

	@Override
	public void deleteBudgetById(Long idBudget) {
		budgetRepository.deleteById(idBudget);
		
	}

	@Override
	public Budget getBudget(Long idBudget) {
		
		return budgetRepository.findById(idBudget).get();
	}

	@Override
	public List<Budget> getAllBudgets() {
		
		return budgetRepository.findAll();
	}

	@Override
	public List<Budget> searchByBudgetName(String budgetName) {
		
		return budgetRepository.findByBudgetNameContainingIgnoreCase(budgetName);
	}

	 @Override
	 public Map<String, Double> calculateBudgetByMonth() {
	     Map<String, Double> budgetByMonth = new HashMap<>();

	     // Get total expenses and revenues by month
	     Map<String, Double> totalDepenseByMonth = depenseService.calculateTotalDepenseByMonth();
	     Map<String, Double> totalRevenusByMonth = revenusService.calculateTotalRevenusByMonth();

	     // Iterate over each month
	     for (String month : totalDepenseByMonth.keySet()) {
	         Double depenseAmount = totalDepenseByMonth.getOrDefault(month, 0.0);
	         Double revenusAmount = totalRevenusByMonth.getOrDefault(month, 0.0);

	         // Calculate the budget for the month
	         Double budget = revenusAmount - depenseAmount;

	         // Store the result in the map
	         budgetByMonth.put(month, budget);
	     }

	     return budgetByMonth;
	 }

	 
}
