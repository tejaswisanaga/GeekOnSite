package com.geekonsite.controller;

import com.geekonsite.dto.*;
import com.geekonsite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CallService callService;
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getDashboard() {
        ApiResponse response = callService.getCallStats();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/agents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createAgent(@RequestBody AgentRegistrationRequest request) {
        ApiResponse response = agentService.registerAgent(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/agents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllAgents() {
        ApiResponse response = agentService.getAllActiveAgents();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllCustomers() {
        ApiResponse response = customerService.getAllCustomers();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/customers/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getPendingCustomers() {
        ApiResponse response = customerService.getPendingVerificationCustomers();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/customers/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> verifyCustomer(@RequestBody VerifyCustomerRequest request) {
        ApiResponse response = customerService.verifyCustomer(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/calls")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllCalls() {
        ApiResponse response = callService.getAllCalls();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/calls/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getPendingCalls() {
        ApiResponse response = callService.getPendingCalls();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/calls/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> assignCall(@RequestBody AssignAgentRequest request) {
        ApiResponse response = callService.assignAgentToCall(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getStats() {
        ApiResponse response = callService.getCallStats();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/init")
    public ResponseEntity<ApiResponse> initializeDefaultAdmin() {
        ApiResponse response = adminService.createDefaultAdmin();
        return ResponseEntity.ok(response);
    }
}
