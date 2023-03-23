package com.eminetekcan.orderservice.repository;

import com.eminetekcan.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
