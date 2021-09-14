package com.putstack.order_service_command.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.putstack.cqrs_axon_common.commands.OrderCancelCommand;
import com.putstack.cqrs_axon_common.commands.OrderCreationCommand;
import com.putstack.order_service_command.client.ProductServiceClient;
import com.putstack.order_service_command.dto.CancelDTO;
import com.putstack.order_service_command.dto.OrderDTO;
import com.putstack.order_service_command.vo.ProductResponse;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CommandGateway commandGateway;
    private final ProductServiceClient productServiceClient;
    private final CircuitBreakerFactory<?,?> circuitBreakerFactory;

    @AggregateIdentifier
    private String orderId;

    @Override
    public CompletableFuture<String> createOrder(OrderDTO orderDTO) {

        ProductResponse response = circuitBreakerFactory
                                    .create("order-product-circuitbreaker")
                                    .run(() -> productServiceClient.getProductInfo(orderDTO.getProductId()));
        
        if (response.getStock() <= 0) throw new RuntimeException("no more stock");

        return commandGateway.send(new OrderCreationCommand(
            UUID.randomUUID().toString(),
            orderDTO.getUserId(), 
            orderDTO.getProductId(), 
            orderDTO.getQty(),
            orderDTO.getQty() * response.getUnitPrice()
            
        ));
    }

    @Override
    public CompletableFuture<String> cancelOrder(CancelDTO cancelDTO) {
        return commandGateway.send(new OrderCancelCommand(
            cancelDTO.getOrderId()
        ));
    }
}
