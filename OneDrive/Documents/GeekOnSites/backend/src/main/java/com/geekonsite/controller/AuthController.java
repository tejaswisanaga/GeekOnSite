package com.geekonsite.controller;

import com.geekonsite.dto.*;
import com.geekonsite.service.AdminService;
import com.geekonsite.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/agent/login")
    public ResponseEntity<ApiResponse> agentLogin(@Valid @RequestBody AgentLoginRequest request) {
        ApiResponse response = agentService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/agent/register")
    public ResponseEntity<ApiResponse> agentRegister(@Valid @RequestBody AgentRegistrationRequest request) {
        ApiResponse response = agentService.registerAgent(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/admin/login")
    public ResponseEntity<ApiResponse> adminLogin(@Valid @RequestBody AdminLoginRequest request) {
        ApiResponse response = adminService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse> adminRegister(@RequestBody AdminRegistrationRequest request) {
        ApiResponse response = adminService.registerAdmin(
            request.getUsername(),
            request.getEmail(),
            request.getPassword(),
            request.getFirstName(),
            request.getLastName()
        );
        return ResponseEntity.ok(response);
    }
    
    // DTO for admin registration
    public static class AdminRegistrationRequest {
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
}
