package com.putstack.user_service_query.service;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import com.putstack.cqrs_axon_common.user.entity.UserDetail;
import com.putstack.user_service_query.query.UserQuery;
import com.putstack.user_service_query.repository.UserRepository;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final Configuration configuration;
    private final UserRepository repository;
    private final QueryGateway gateway;

    @Override
    public void reset() {
        configuration.eventProcessingConfiguration()
            .eventProcessorByProcessingGroup("orders", TrackingEventProcessor.class)
            .ifPresent(processor -> {
                processor.shutDown();
                processor.resetTokens();
                processor.start();
            });
    }

    @Override
    public UserDetail getUserInfo(String orderId) {
        return gateway.query(new UserQuery(orderId), UserDetail.class).join();
    }
    
    @Override
    public UserDetail findByEmail(String email) throws EntityNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Repository
        UserDetail UserDetail = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        // Return
        return new User(
            UserDetail.getEmail(),
            UserDetail.getPassword(),
            true, // enabled
            true, // accountNonExpired
            true, // credentialsNonExpired  
            true, // accountNonLocked
            new ArrayList<>() // authorities
        );
    }


}
