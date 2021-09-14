package com.putstack.catalog_service_command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductPurchaseDTO {
    private String productId;
    private int qty;
}
