package com.vaultwise.controller;

import com.vaultwise.model.Expense;
import com.vaultwise.model.User;
import com.vaultwise.service.ExpenseService;
import com.vaultwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/expense")
    public ResponseEntity<?> addExpense(@PathVariable Long userId, @RequestBody Expense expense) {
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            expense.setUser(userOpt.get());
            Expense savedExpense = expenseService.saveExpense(expense);
            return ResponseEntity.ok(savedExpense);
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    @GetMapping("/{userId}/expenses")
    public ResponseEntity<?> getAllExpenses(@PathVariable Long userId) {
        try {
            System.out.println("Fetching expenses for userId = " + userId);
            List<Expense> expenses = expenseService.getAllExpensesForUser(userId);
            System.out.println("Expenses size = " + expenses.size());
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error: " + e.getMessage());
        }
    }

    

    @DeleteMapping("/expense/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
