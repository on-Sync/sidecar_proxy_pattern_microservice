package com.putstack.cqrs_axon_common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductPurchaseEvent {
    private String productId;
    private int qty;
}
