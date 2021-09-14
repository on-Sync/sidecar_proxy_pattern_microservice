package com.putstack.order_service_query.projection;

import java.time.Instant;
import java.util.NoSuchElementException;

import com.putstack.cqrs_axon_common.events.OrderCancelEvent;
import com.putstack.cqrs_axon_common.events.OrderCreationEvent;
import com.putstack.order_service_query.entity.OrderSummary;
import com.putstack.order_service_query.query.OrderQuery;
import com.putstack.order_service_query.repository.OrderRepository;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
@ProcessingGroup(value = "orders")
public class OrderProjection {
    private final OrderRepository repository;

    private final static int ORDER_RECEIVED = 11;
    private final static int ORDER_CANCELED = 22;
    private final static int ORDER_DEPARTED = 33;
    private final static int ORDER_ARRIVED = 44;

    @ResetHandler
    private void resetOrderInfo() {
        log.debug("reset ReadModel<OrderEntity>");
        repository.deleteAll();
    }

    @EventHandler
    @AllowReplay
    public void on(OrderCreationEvent event, @Timestamp Instant instant) {
        log.debug("projection {}, timestamp : {}", event, instant.toString());

        OrderSummary entity = OrderSummary
                                .builder()
                                .orderId(event.getOrderId())
                                .userId(event.getUserId())
                                .productId(event.getProductId())
                                .qty(event.getQty())
                                .unitPrice(event.getUnitPrice())
                                .totalPrice(event.getTotalPrice())
                                .status(ORDER_RECEIVED)
                                .build();

        repository.save(entity);
    }

    @EventHandler
    @AllowReplay
    public void on(OrderCancelEvent event, @Timestamp Instant instant) {
        log.debug("projection {}, timestamp : {}", event, instant.toString());

        OrderSummary entity = getOrderSummary(event.getOrderId());
        entity.setStatus(ORDER_CANCELED);
        repository.save(entity);
    }

    @QueryHandler
    public OrderSummary on(OrderQuery query){
        log.debug("handling {}", query);
        return getOrderSummary(query.getOrderId());
    }

    private OrderSummary getOrderSummary(String orderId) {
        return repository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("주문이 존재하지 않습니다."));
    }

    
}
