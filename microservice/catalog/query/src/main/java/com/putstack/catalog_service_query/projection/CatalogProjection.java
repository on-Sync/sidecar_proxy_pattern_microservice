package com.putstack.catalog_service_query.projection;

import java.time.Instant;
import java.util.NoSuchElementException;

import com.putstack.catalog_service_query.entity.ProductSummary;
import com.putstack.catalog_service_query.query.CatalogQuery;
import com.putstack.catalog_service_query.repository.CatalogRepository;
import com.putstack.cqrs_axon_common.events.ProductPurchaseEvent;
import com.putstack.cqrs_axon_common.events.ProductRegisterEvent;

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
@ProcessingGroup(value = "products")
public class CatalogProjection {
    private final CatalogRepository repository;

    private final static int PRODUCT_SALE_ON = 11;
    private final static int PRODUCT_SALE_OFF = 22;

    @ResetHandler
    private void resetCatalogInfo() {
        log.debug("reset ReadModel<CatalogEntity>");
        repository.deleteAll();
    }

    @EventHandler
    @AllowReplay
    public void on(ProductRegisterEvent event, @Timestamp Instant instant) {
        log.debug("projection {}, timestamp : {}", event, instant.toString());

        ProductSummary entity = ProductSummary
                                .builder()
                                .productId(event.getProductId())
                                .productName(event.getProductName())
                                .sellerId(event.getSellerId())
                                .categoryCode(event.getCategoryCode())
                                .stock(event.getStock())
                                .unitPrice(event.getUnitPrice())
                                .status(PRODUCT_SALE_ON)
                                .build();

        repository.save(entity);
    }

    @EventHandler
    @AllowReplay
    public void on(ProductPurchaseEvent event, @Timestamp Instant instant) {
        log.debug("projection {}, timestamp : {}", event, instant.toString());

        ProductSummary entity = getProductSummary(event.getProductId());
        entity.setStock(entity.getStock() - event.getQty());
        repository.save(entity);
    }

    @QueryHandler
    public ProductSummary on(CatalogQuery query){
        log.debug("handling {}", query);
        return getProductSummary(query.getProductId());
    }

    private ProductSummary getProductSummary(String productId) {
        return repository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("제품이 존재하지 않습니다."));
    }
}
