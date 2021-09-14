package com.putstack.cqrs_axon_common.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProductStartSaleCommand {
    @TargetAggregateIdentifier
    private String productId;
}
