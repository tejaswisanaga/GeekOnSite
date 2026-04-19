package com.geekonsite.security;

import com.geekonsite.model.Admin;
import com.geekonsite.model.Agent;
import com.geekonsite.repository.AdminRepository;
import com.geekonsite.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private AgentRepository agentRepository;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String compositeUsername) throws UsernameNotFoundException {
        String[] parts = compositeUsername.split(":");
        String username = parts[0];
        String role = parts.length > 1 ? parts[1] : "AGENT";
        
        if ("ADMIN".equals(role)) {
            Optional<Admin> adminOpt = adminRepository.findByUsername(username);
            if (adminOpt.isEmpty()) {
                adminOpt = adminRepository.findByEmail(username);
            }
            
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                return new User(
                    admin.getUsername(),
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                );
            }
        } else {
            Optional<Agent> agentOpt = agentRepository.findByAgentId(username);
            if (agentOpt.isEmpty()) {
                agentOpt = agentRepository.findByEmail(username);
            }
            
            if (agentOpt.isPresent()) {
                Agent agent = agentOpt.get();
                return new User(
                    agent.getAgentId(),
                    agent.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_AGENT"))
                );
            }
        }
        
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
