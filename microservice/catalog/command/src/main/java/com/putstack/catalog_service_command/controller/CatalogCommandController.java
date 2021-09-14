package com.putstack.catalog_service_command.controller;

import java.util.concurrent.CompletableFuture;

import com.putstack.catalog_service_command.dto.ProductPurchaseDTO;
import com.putstack.catalog_service_command.dto.ProductRegisterDTO;
import com.putstack.catalog_service_command.service.CatalogCommandService;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@Log4j2
public class CatalogCommandController {
    private final CatalogCommandService orderService;

    @PostMapping(value="/products")
    public CompletableFuture<String> register(@RequestBody ProductRegisterDTO registerDTO) {
        return orderService.registerProduct(registerDTO);
    }

    @ExceptionHandler
    @PostMapping(value="/products/{productId}")
    public CompletableFuture<String> purchase(@PathVariable String productId, @RequestBody ProductPurchaseDTO registerDTO) {
        log.debug("purchase {}", productId);
        log.debug("purchase {}", registerDTO.getProductId());

        if (! productId.equals(registerDTO.getProductId())) {
            return CompletableFuture.failedFuture(new Exception("Bad Request"));
        }

        return orderService.purchaseProduct(registerDTO);
    }
}
