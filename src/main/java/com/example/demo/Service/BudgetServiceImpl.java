package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Budget;
import com.example.demo.Repository.BudgetRepository;

@Service
public class BudgetServiceImpl implements BudgetService {
@Autowired
BudgetRepository budgetRepository;
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

}
