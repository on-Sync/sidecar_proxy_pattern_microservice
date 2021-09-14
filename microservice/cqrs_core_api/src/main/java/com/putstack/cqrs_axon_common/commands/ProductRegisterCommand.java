package com.putstack.cqrs_axon_common.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProductRegisterCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String productName;
    private String sellerId;
    private int categoryCode;
    private int stock;
    private int unitPrice;
}
