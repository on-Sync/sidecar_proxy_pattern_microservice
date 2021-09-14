package com.putstack.order_service_command.controller;

import java.util.concurrent.CompletableFuture;

import com.putstack.order_service_command.dto.CancelDTO;
import com.putstack.order_service_command.dto.OrderDTO;
import com.putstack.order_service_command.service.OrderCommandService;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class OrderCommandController {
    private final OrderCommandService orderService;

    @PostMapping(value="/orders")
    public CompletableFuture<String> createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @DeleteMapping(value="/orders/{orderId}")
    public CompletableFuture<String> cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(new CancelDTO(orderId));
    }
}
