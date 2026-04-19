package com.geekonsite.repository;

import com.geekonsite.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    
    Optional<Admin> findByUsername(String username);
    
    Optional<Admin> findByEmail(String email);
    
    Optional<Admin> findByUsernameOrEmail(String username, String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}
