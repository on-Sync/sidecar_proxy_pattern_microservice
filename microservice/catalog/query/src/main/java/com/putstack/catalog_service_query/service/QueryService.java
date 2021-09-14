package com.putstack.catalog_service_query.service;

import com.putstack.catalog_service_query.entity.ProductSummary;

public interface QueryService {
    public void reset();
    public ProductSummary getCatalogInfo(String orderId);
}
