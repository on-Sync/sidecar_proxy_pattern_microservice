package com.putstack.order_service_query.repository;

import com.putstack.order_service_query.entity.OrderSummary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderSummary, String> {

}
