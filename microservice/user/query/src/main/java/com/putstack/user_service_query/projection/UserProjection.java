package com.putstack.user_service_query.projection;

import java.time.Instant;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.putstack.cqrs_axon_common.user.entity.UserDetail;
import com.putstack.cqrs_axon_common.user.events.UserCreationEvent;
import com.putstack.user_service_query.query.UserQuery;
import com.putstack.user_service_query.repository.UserRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
@ProcessingGroup(value = "orders")
public class UserProjection {
    private final UserRepository repository;

    private final static int USER_JOINED = 11;
    private final static int USER_SECESSED = 22;

    @ResetHandler
    private void resetUserInfo() {
        log.debug("reset ReadModel<UserEntity>");
        repository.deleteAll();
    }

    @EventHandler
    @AllowReplay
    @Transactional
    public void on(UserCreationEvent event, @Timestamp Instant instant) {
        log.debug("projection {}, timestamp : {}", event.getMembership(), instant.toString());

        UserDetail entity = UserDetail
                                .builder()
                                .userId(event.getUserId())
                                .email(event.getEmail())
                                .password(event.getPassword())
                                .name(event.getName())
                                .age(event.getAge())
                                .address(event.getAddress())
                                .ssn(event.getSsn())
                                .address(event.getAddress())
                                .membership(event.getMembership())
                                .build();

        // System.out.println(entity.getMembership().getMembershipId());

        repository.save(entity);
    }

    @QueryHandler
    public UserDetail on(UserQuery query){
        log.debug("handling {}", query);
        return getUserDetail(query.getUserId());
    }

    private UserDetail getUserDetail(String userId) {
        return repository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));
    }

    
}
