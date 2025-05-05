package com.vaultwise.service;

import com.vaultwise.model.Expense;
import com.vaultwise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpensesForUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
