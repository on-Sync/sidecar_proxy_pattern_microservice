package com.putstack.user_service_command.service;

import java.util.concurrent.CompletableFuture;

import com.putstack.cqrs_axon_common.user.dto.UserDTO;

public interface UserCommandService {
    // User
    CompletableFuture<String> createUser(UserDTO userDTO);
    CompletableFuture<String> updateUser(UserDTO userDTO);
    CompletableFuture<String> deleteUser(UserDTO userDTO);
}
