package com.putstack.user_service_query.service;

import javax.persistence.EntityNotFoundException;

import com.putstack.cqrs_axon_common.user.entity.UserDetail;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface QueryService extends UserDetailsService {
    public void reset();
    public UserDetail getUserInfo(String orderId);
    public UserDetail findByEmail(String email) throws EntityNotFoundException;
}
