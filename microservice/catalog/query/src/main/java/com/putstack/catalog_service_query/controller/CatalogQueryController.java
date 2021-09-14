package com.putstack.catalog_service_query.controller;

import javax.validation.constraints.NotBlank;

import com.putstack.catalog_service_query.entity.ProductSummary;
import com.putstack.catalog_service_query.service.QueryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CatalogQueryController {
    private final QueryService queryService;

    @GetMapping(path = "/products/{productId}")
    public ResponseEntity<ProductSummary> getProductInfo(@PathVariable @NonNull @NotBlank String productId){
        return ResponseEntity.status(HttpStatus.OK)
                             .body(queryService.getCatalogInfo(productId));
    }

}
