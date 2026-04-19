package com.geekonsite.config;

import com.geekonsite.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartupConfig implements CommandLineRunner {
    
    @Autowired
    private AdminService adminService;
    
    @Override
    public void run(String... args) {
        log.info("Initializing GeekOnSite Call Management System...");
        
        // Create default admin on startup
        var response = adminService.createDefaultAdmin();
        if (response.isSuccess()) {
            log.info("✓ {}", response.getMessage());
            log.info("✓ Default admin credentials: admin / admin123");
        }
        
        log.info("✓ System ready at http://localhost:8080");
        log.info("✓ API Documentation:");
        log.info("  - Public endpoints: /api/public/**");
        log.info("  - Auth endpoints: /api/auth/**");
        log.info("  - Agent endpoints: /api/agent/**");
        log.info("  - Admin endpoints: /api/admin/**");
        log.info("  - Customer endpoints: /api/customers/**");
        log.info("  - Call endpoints: /api/calls/**");
    }
}
