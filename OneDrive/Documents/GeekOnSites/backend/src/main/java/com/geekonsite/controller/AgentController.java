package com.geekonsite.controller;

import com.geekonsite.dto.ApiResponse;
import com.geekonsite.model.SupportTicket;
import com.geekonsite.repository.SupportTicketRepository;
import com.geekonsite.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "*")
public class AgentController {
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private SupportTicketRepository supportTicketRepository;
    
    /**
     * Get agent's assigned tickets
     * GET /api/agent/tickets
     */
    @GetMapping("/tickets")
    public ResponseEntity<ApiResponse> getMyTickets(@RequestHeader("Authorization") String authorization) {
        try {
            // Extract agent info from token (simple implementation)
            String token = authorization.replace("Bearer ", "");
            if (!token.startsWith("agent-token-")) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid agent token"));
            }
            
            // Extract agent ID from token (format: agent-token-{agentId}-{timestamp})
            String[] tokenParts = token.split("-");
            String agentId = tokenParts[2];
            
            // Get tickets assigned to this agent
            List<SupportTicket> assignedTickets = supportTicketRepository.findByAssignedTo(agentId);
            
            return ResponseEntity.ok(ApiResponse.success("Agent tickets retrieved", assignedTickets));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to get tickets: " + e.getMessage()));
        }
    }
    
    /**
     * Get agent's tickets by status
     * GET /api/agent/tickets/status/{status}
     */
    @GetMapping("/tickets/status/{status}")
    public ResponseEntity<ApiResponse> getTicketsByStatus(
            @PathVariable String status,
            @RequestHeader("Authorization") String authorization) {
        try {
            // Extract agent ID from token
            String token = authorization.replace("Bearer ", "");
            if (!token.startsWith("agent-token-")) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid agent token"));
            }
            String[] tokenParts = token.split("-");
            String agentId = tokenParts[2];
            
            // Get tickets assigned to this agent with specific status
            List<SupportTicket> allAssignedTickets = supportTicketRepository.findByAssignedTo(agentId);
            SupportTicket.TicketStatus ticketStatus = SupportTicket.TicketStatus.valueOf(status.toUpperCase());
            
            List<SupportTicket> filteredTickets = allAssignedTickets.stream()
                .filter(ticket -> ticket.getStatus() == ticketStatus)
                .toList();
            
            return ResponseEntity.ok(ApiResponse.success(status + " tickets retrieved", filteredTickets));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to get tickets: " + e.getMessage()));
        }
    }
    
    /**
     * Update ticket status
     * PUT /api/agent/tickets/{ticketNumber}/status
     */
    @PutMapping("/tickets/{ticketNumber}/status")
    public ResponseEntity<ApiResponse> updateTicketStatus(
            @PathVariable String ticketNumber,
            @RequestBody Map<String, Object> request,
            @RequestHeader("Authorization") String authorization) {
        try {
            // Extract agent ID from token
            String token = authorization.replace("Bearer ", "");
            if (!token.startsWith("agent-token-")) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid agent token"));
            }
            String[] tokenParts = token.split("-");
            String agentId = tokenParts[2];
            
            // Find ticket
            Optional<SupportTicket> ticketOpt = supportTicketRepository.findByTicketNumber(ticketNumber);
            if (ticketOpt.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.error("Ticket not found"));
            }
            
            SupportTicket ticket = ticketOpt.get();
            
            // Verify ticket is assigned to this agent
            if (!agentId.equals(ticket.getAssignedTo())) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Ticket not assigned to this agent"));
            }
            
            // Update ticket
            String status = (String) request.get("status");
            String notes = (String) request.get("notes");
            
            SupportTicket.TicketStatus newStatus = SupportTicket.TicketStatus.valueOf(status.toUpperCase());
            ticket.setStatus(newStatus);
            ticket.setUpdatedAt(LocalDateTime.now());
            
            if (notes != null && !notes.trim().isEmpty()) {
                ticket.addNote(notes, "agent", false);
            }
            
            supportTicketRepository.save(ticket);
            
            return ResponseEntity.ok(ApiResponse.success("Ticket status updated successfully", ticket));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to update ticket: " + e.getMessage()));
        }
    }
    
    /**
     * Get agent dashboard stats
     * GET /api/agent/dashboard
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getDashboard(@RequestHeader("Authorization") String authorization) {
        try {
            // Extract agent ID from token
            String token = authorization.replace("Bearer ", "");
            if (!token.startsWith("agent-token-")) {
                return ResponseEntity.badRequest().body(ApiResponse.error("Invalid agent token"));
            }
            String[] tokenParts = token.split("-");
            String agentId = tokenParts[2];
            
            // Get all assigned tickets
            List<SupportTicket> assignedTickets = supportTicketRepository.findByAssignedTo(agentId);
            
            // Calculate stats
            long pendingCount = assignedTickets.stream().filter(t -> t.getStatus() == SupportTicket.TicketStatus.OPEN).count();
            long inProgressCount = assignedTickets.stream().filter(t -> t.getStatus() == SupportTicket.TicketStatus.IN_PROGRESS).count();
            long completedCount = assignedTickets.stream().filter(t -> t.getStatus() == SupportTicket.TicketStatus.RESOLVED || t.getStatus() == SupportTicket.TicketStatus.CLOSED).count();
            
            Map<String, Object> stats = Map.of(
                "totalTickets", assignedTickets.size(),
                "pendingTickets", pendingCount,
                "inProgressTickets", inProgressCount,
                "completedTickets", completedCount,
                "assignedTickets", assignedTickets
            );
            
            return ResponseEntity.ok(ApiResponse.success("Dashboard data retrieved", stats));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to get dashboard: " + e.getMessage()));
        }
    }
    
    /**
     * Get list of all agents (for admin)
     * GET /api/agent/list
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getAllAgents() {
        ApiResponse response = agentService.getAllActiveAgents();
        return ResponseEntity.ok(response);
    }
}
