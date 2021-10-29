package com.putstack.user_service_query.repository;

import java.util.Optional;

import com.putstack.cqrs_axon_common.user.entity.UserDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, String> {
    Optional<UserDetail> findByEmail(String email); 
}
