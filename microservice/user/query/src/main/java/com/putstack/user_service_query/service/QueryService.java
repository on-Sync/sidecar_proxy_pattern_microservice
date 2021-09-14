package com.putstack.user_service_query.service;

import javax.persistence.EntityNotFoundException;

import com.putstack.user_service_query.entity.UserSummary;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface QueryService extends UserDetailsService {
    public void reset();
    public UserSummary getUserInfo(String orderId);
    public UserSummary findByEmail(String email) throws EntityNotFoundException;
}
