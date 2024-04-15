package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Budget;


public interface BudgetRepository extends JpaRepository<Budget, Long> {
	 List<Budget> findByBudgetNameContainingIgnoreCase(String budgetName);
}
