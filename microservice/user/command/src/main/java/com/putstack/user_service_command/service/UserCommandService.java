package com.putstack.user_service_command.service;

import java.util.concurrent.CompletableFuture;

import com.putstack.user_service_command.dto.UserDTO;

public interface UserCommandService {
    CompletableFuture<String> createUser(UserDTO userDTO);
}
