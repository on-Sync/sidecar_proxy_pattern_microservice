package com.putstack.order_service_command.aggregate;


import java.util.Date;

import com.putstack.cqrs_axon_common.commands.OrderCancelCommand;
import com.putstack.cqrs_axon_common.commands.OrderCreationCommand;
import com.putstack.cqrs_axon_common.events.OrderCancelEvent;
import com.putstack.cqrs_axon_common.events.OrderCreationEvent;

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
@Aggregate
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String productId;
    private int qty;
    private int unitPrice;
    private int totalPrice;
    private Date createAt;
    private Date updateAt;

    @CommandHandler
    public OrderAggregate(OrderCreationCommand command) {
        log.debug("CommandHandler {}", command);
        
        AggregateLifecycle.apply(new OrderCreationEvent(
            command.getOrderId(), 
            command.getUserId(), 
            command.getProductId(), 
            command.getQty(), 
            command.getUnitPrice(), 
            command.getUnitPrice() * command.getQty(),
            new Date(System.currentTimeMillis()),
            new Date(System.currentTimeMillis())
        ));
    }

    @EventSourcingHandler
    public void createOrder(OrderCreationEvent event) {
        log.debug("AggregateLifecycle.apply {}", event);

        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.productId = event.getProductId();
        this.qty = event.getQty();
        this.unitPrice = event.getUnitPrice();
        this.totalPrice = event.getTotalPrice();
    }

    @CommandHandler
    public void cancelOrder(OrderCancelCommand command) {
        log.debug("CommandHandler {}", command);

        AggregateLifecycle.apply(new OrderCancelEvent(
            command.getOrderId(),
            new Date(System.currentTimeMillis())
        ));
    }

    @EventSourcingHandler
    public void cancelOrder(OrderCancelEvent event) {
        log.debug("AggregateLifecycle.apply {}", event);

        this.orderId = event.getOrderId();
    }
}
