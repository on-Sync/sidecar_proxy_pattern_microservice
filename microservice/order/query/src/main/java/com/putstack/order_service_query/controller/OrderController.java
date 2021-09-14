package com.putstack.order_service_query.controller;

import javax.validation.constraints.NotBlank;

import com.putstack.order_service_query.entity.OrderSummary;
import com.putstack.order_service_query.service.QueryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final QueryService queryService;

    @PostMapping(path = "/reset")
    public ResponseEntity<?> reset() {
        queryService.reset();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/orders/{orderId}")
    public ResponseEntity<OrderSummary> getOrderInfo(@PathVariable @NonNull @NotBlank String orderId){
        return ResponseEntity.status(HttpStatus.OK)
                             .body(queryService.getOrderInfo(orderId));
    }

}
