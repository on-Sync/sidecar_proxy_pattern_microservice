package com.putstack.order_service_query.service;

import com.putstack.order_service_query.entity.OrderSummary;

public interface QueryService {
    public void reset();
    public OrderSummary getOrderInfo(String orderId);
}
