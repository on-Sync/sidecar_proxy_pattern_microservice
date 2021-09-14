package com.putstack.order_service_command.transaction;

import javax.inject.Inject;

import com.putstack.cqrs_axon_common.commands.ProductPurchaseCommand;
import com.putstack.cqrs_axon_common.events.OrderCreationEvent;
import com.putstack.cqrs_axon_common.events.ProductPurchaseEvent;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import lombok.extern.log4j.Log4j2;

@Saga
@Log4j2
public class OrderManagerSaga {

    private boolean paid = false;
    private boolean rollback = false;
    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreationEvent event) {
        log.info("SAGA : OrderCreationEvent {} ", event.getOrderId(), event.getProductId());

        SagaLifecycle.associateWith("productId", event.getProductId());
        commandGateway.send(new ProductPurchaseCommand(event.getProductId(), event.getQty()));
    }

    @SagaEventHandler(associationProperty = "productId")
    public void handle(ProductPurchaseEvent event) {
        log.info("SAGA : ProductPurchaseEvent {} ", event.getProductId());
        // delivered = true;
        // if (rollback) { SagaLifecycle.end(); }
        SagaLifecycle.end();
    }

    // @EndSaga
    // @SagaEventHandler(associationProperty = "invoiceId")
    // public void handle(InvoicePaidEvent event) {
    //     paid = true;
    //     if (delivered) { SagaLifecycle.end(); }
    // }
}
