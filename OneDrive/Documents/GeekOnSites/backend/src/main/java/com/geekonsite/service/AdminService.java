package com.geekonsite.service;

import com.geekonsite.dto.*;
import com.geekonsite.model.Admin;
import com.geekonsite.repository.AdminRepository;
import com.geekonsite.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    public ApiResponse createDefaultAdmin() {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin(
                "admin",
                "admin@geekonsite.com",
                passwordEncoder.encode("admin123"),
                "System",
                "Administrator"
            );
            adminRepository.save(admin);
            return ApiResponse.success("Default admin created");
        }
        return ApiResponse.success("Admin already exists");
    }
    
    public ApiResponse registerAdmin(String username, String email, String password, String firstName, String lastName) {
        if (adminRepository.existsByUsername(username)) {
            return ApiResponse.error("Username already exists");
        }
        
        if (adminRepository.existsByEmail(email)) {
            return ApiResponse.error("Email already registered");
        }
        
        Admin admin = new Admin(
            username,
            email,
            passwordEncoder.encode(password),
            firstName,
            lastName
        );
        
        adminRepository.save(admin);
        return ApiResponse.success("Admin registered successfully");
    }
    
    public ApiResponse login(AdminLoginRequest request) {
        try {
            String username = request.getUsernameOrEmail();
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username + ":ADMIN", request.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            Optional<Admin> adminOpt = adminRepository.findByUsername(username);
            if (adminOpt.isEmpty()) {
                adminOpt = adminRepository.findByEmail(username);
            }
            
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                admin.setLastLogin(LocalDateTime.now());
                adminRepository.save(admin);
                
                String token = jwtUtils.generateJwtToken(admin.getUsername(), "ADMIN");
                
                LoginResponse response = new LoginResponse(
                    token,
                    admin.getId(),
                    admin.getUsername(),
                    admin.getEmail(),
                    admin.getFirstName(),
                    admin.getLastName(),
                    List.of("ADMIN")
                );
                
                return ApiResponse.success("Login successful", response);
            }
            
            return ApiResponse.error("Admin not found");
        } catch (Exception e) {
            return ApiResponse.error("Invalid credentials: " + e.getMessage());
        }
    }
    
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
