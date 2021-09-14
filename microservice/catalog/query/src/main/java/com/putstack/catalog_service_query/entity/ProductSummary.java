package com.putstack.catalog_service_query.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummary {
    @Id
    private String productId;
    private String productName;
    private String sellerId;
    private int categoryCode;
    private int stock;
    private int unitPrice;
    private int status;
}
