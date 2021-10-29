package com.putstack.cqrs_axon_common.user.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserUpdatePasswordCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String password;
}
