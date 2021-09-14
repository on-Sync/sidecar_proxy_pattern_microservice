package com.putstack.user_service_query.repository;

import java.util.Optional;

import com.putstack.user_service_query.entity.UserSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserSummary, String> {
    Optional<UserSummary> findByEmail(String email); 
}
