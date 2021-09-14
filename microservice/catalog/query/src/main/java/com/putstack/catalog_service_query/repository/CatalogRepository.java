package com.putstack.catalog_service_query.repository;

import com.putstack.catalog_service_query.entity.ProductSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<ProductSummary, String> {

}
