package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import com.example.demo.Model.Budget;


public interface BudgetService {
	Budget saveBudget(Budget budget);
	Budget UpdateBudget(Budget budget);
	void deleteBudget(Budget budget);
	void deleteBudgetById(Long idBudget);
	Budget getBudget(Long idBudget);
	List<Budget> getAllBudgets();
	List<Budget> searchByBudgetName(String budgetName);
	 Map<String, Double> calculateBudgetByMonth();


}
