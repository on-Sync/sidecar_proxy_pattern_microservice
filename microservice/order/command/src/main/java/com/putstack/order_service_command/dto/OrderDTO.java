package com.putstack.order_service_command.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDTO {
    private String userId;
    private String productId;
    private int qty;
}
