package com.putstack.cqrs_axon_common.user.commands;

import com.putstack.cqrs_axon_common.user.entity.Membership;
import com.putstack.cqrs_axon_common.user.entity.UserAddress;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserCreationCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String email;
    private String password;
    private String name;
    private int age;
    private String ssn;
    private UserAddress address;
    private Membership membership;
}
