package com.putstack.order_service_command.vo;

import lombok.Getter;

@Getter
public class ProductResponse {
    private String productId;
    private String productName;
    private String sellerId;
    private int categoryCode;
    private int stock;
    private int unitPrice;
    private int status;
}
