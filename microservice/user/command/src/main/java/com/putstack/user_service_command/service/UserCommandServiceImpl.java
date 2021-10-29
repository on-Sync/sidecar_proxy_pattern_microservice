package com.putstack.user_service_command.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.putstack.cqrs_axon_common.user.commands.UserCreationCommand;
import com.putstack.cqrs_axon_common.user.dto.UserDTO;
import com.putstack.cqrs_axon_common.user.entity.Membership;
import com.putstack.cqrs_axon_common.user.entity.UserAddress;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final CommandGateway commandGateway;

    @AggregateIdentifier
    private String orderId;

    @Override
    public CompletableFuture<String> createUser(UserDTO userDTO) {
        return commandGateway.send(new UserCreationCommand(
            UUID.randomUUID().toString(),
            userDTO.getEmail(),
            PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userDTO.getPassword()),
            userDTO.getName(),
            userDTO.getAge(),
            userDTO.getSsn(),
            userDTO.getAddress(),
            userDTO.getMembership()
        ));
    }

    @Override
    public CompletableFuture<String> updateUser(UserDTO userDTO) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public CompletableFuture<String> deleteUser(UserDTO userDTO) {
        // TODO Auto-generated method stub
        return null;
    }
}
