package com.putstack.order_service_query.service;

import com.putstack.order_service_query.entity.OrderSummary;
import com.putstack.order_service_query.query.OrderQuery;
import com.putstack.order_service_query.repository.OrderRepository;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final Configuration configuration;
    private final OrderRepository repository;
    private final QueryGateway gateway;

    @Override
    public void reset() {
        configuration.eventProcessingConfiguration()
            .eventProcessorByProcessingGroup("orders", TrackingEventProcessor.class)
            .ifPresent(processor -> {
                processor.shutDown();
                processor.resetTokens();
                processor.start();
            });
    }

    @Override
    public OrderSummary getOrderInfo(String orderId) {
        return gateway.query(new OrderQuery(orderId), OrderSummary.class).join();
    }


}
