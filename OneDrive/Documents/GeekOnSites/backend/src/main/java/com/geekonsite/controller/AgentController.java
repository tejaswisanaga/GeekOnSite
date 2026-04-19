package com.geekonsite.controller;

import com.geekonsite.dto.*;
import com.geekonsite.service.AgentService;
import com.geekonsite.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentController {
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private CallService callService;
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getDashboard(Authentication authentication) {
        String agentId = authentication.getName();
        ApiResponse response = agentService.getAgentDashboard(agentId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/calls")
    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getMyCalls(Authentication authentication) {
        String agentId = authentication.getName();
        ApiResponse response = callService.getCallsByAgent(agentId);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/calls/status/{status}")
    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getCallsByStatus(
            @PathVariable String status,
            Authentication authentication) {
        String agentId = authentication.getName();
        ApiResponse response = callService.getCallsByAgent(agentId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/calls/{callId}/status")
    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateCallStatus(
            @PathVariable String callId,
            @RequestBody CallStatusUpdateRequest request) {
        ApiResponse response = callService.updateCallStatus(callId, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getAllAgents() {
        ApiResponse response = agentService.getAllActiveAgents();
        return ResponseEntity.ok(response);
    }
}
