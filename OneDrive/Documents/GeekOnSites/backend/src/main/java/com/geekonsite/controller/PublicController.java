package com.geekonsite.controller;

import com.geekonsite.dto.ApiResponse;
import com.geekonsite.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/health")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Service is running"));
    }
    
    @PostMapping("/init")
    public ResponseEntity<ApiResponse> initializeSystem() {
        // Create default admin if no admins exist
        ApiResponse response = adminService.createDefaultAdmin();
        return ResponseEntity.ok(response);
    }
}
