package com.putstack.cqrs_axon_common.user.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserUpdateInfoCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String name;
    private int age;
    private String address;
    private String ssn;
}
