package com.vaultwise.controller;

import com.vaultwise.model.Expense;
import com.vaultwise.model.User;
import com.vaultwise.security.AuthResponse;
import com.vaultwise.security.JwtUtil;
import com.vaultwise.service.ExpenseService;
import com.vaultwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Public - Register
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.save(user);
    }
    @GetMapping("/ping")
    public String ping() {
        return "VaultWise backend is running!";
    }


    // ✅ Public - Login and return JWT
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody User loginUser) {
        Optional<User> user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(loginUser.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, user.get().getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // 🔐 Secure - Add Expense (with token)
    
    
    
    

   
    
}
