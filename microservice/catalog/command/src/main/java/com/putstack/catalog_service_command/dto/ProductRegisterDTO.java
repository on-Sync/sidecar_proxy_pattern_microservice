package com.putstack.catalog_service_command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductRegisterDTO {
    private String productName;
    private String sellerId;
    private int categoryCode;
    private int stock;
    private int unitPrice;
}
