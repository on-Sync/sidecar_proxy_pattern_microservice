package com.putstack.order_service_command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelDTO {
    private String orderId;
}
