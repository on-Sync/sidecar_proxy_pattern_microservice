package com.putstack.user_service_command.controller;

import java.util.concurrent.CompletableFuture;

import com.putstack.cqrs_axon_common.user.dto.UserDTO;
import com.putstack.user_service_command.service.UserCommandService;

import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class UserCommandController {
    private final UserCommandService orderService;

    @PostMapping(value="/users")
    public CompletableFuture<String> createUser(@RequestBody UserDTO orderDTO) {
        return orderService.createUser(orderDTO);
    }

    // @PutMapping
    // public CompletableFuture<String> updateUser name(@RequestBody UserDTO orderDTO) {
    //     return orderService.
    // }
}
