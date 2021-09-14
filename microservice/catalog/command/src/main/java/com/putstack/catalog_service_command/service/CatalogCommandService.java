package com.putstack.catalog_service_command.service;

import java.util.concurrent.CompletableFuture;

import com.putstack.catalog_service_command.dto.ProductPurchaseDTO;
import com.putstack.catalog_service_command.dto.ProductRegisterDTO;

public interface CatalogCommandService {
    CompletableFuture<String> registerProduct(ProductRegisterDTO productDTO);
    CompletableFuture<String> purchaseProduct(ProductPurchaseDTO productDTO);
}
