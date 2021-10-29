package com.putstack.user_service_command.aggregate;

import java.util.UUID;

import com.putstack.cqrs_axon_common.user.commands.UserCreationCommand;
import com.putstack.cqrs_axon_common.user.entity.Membership;
import com.putstack.cqrs_axon_common.user.entity.UserAddress;
import com.putstack.cqrs_axon_common.user.events.UserCreationEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Aggregate()
@Slf4j
public class UserAggregate {
    @AggregateIdentifier
    private String userId;
    private String email;
    private String password;
    private String name;
    private int age;
    private String ssn;
    private String phone;
    private UserAddress address;
    private Membership membership;

    @CommandHandler
    public UserAggregate(UserCreationCommand command) {
        log.debug("CommandHandler {}", command);
        AggregateLifecycle.apply(new UserCreationEvent(
            UUID.randomUUID().toString(),
            command.getEmail(),
            command.getPassword(),
            command.getName(),
            command.getAge(),
            command.getSsn(),
            command.getAddress(),
            command.getMembership()
        ));
    }

    @EventSourcingHandler
    public void createUser(UserCreationEvent event) {
        log.debug("AggregateLifecycle.apply {}", event);
        this.userId = event.getUserId();
        this.email = event.getEmail();
        this.password = event.getPassword();
        this.name = event.getName();
        this.age = event.getAge();
        this.ssn = event.getSsn();
        this.address = event.getAddress();
        this.membership = event.getMembership();
    }
}
