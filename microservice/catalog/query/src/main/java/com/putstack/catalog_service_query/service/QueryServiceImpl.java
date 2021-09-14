package com.putstack.catalog_service_query.service;

import com.putstack.catalog_service_query.entity.ProductSummary;
import com.putstack.catalog_service_query.query.CatalogQuery;
import com.putstack.catalog_service_query.repository.CatalogRepository;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final Configuration configuration;
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
    public ProductSummary getCatalogInfo(String orderId) {
        return gateway.query(new CatalogQuery(orderId), ProductSummary.class).join();
    }


}
