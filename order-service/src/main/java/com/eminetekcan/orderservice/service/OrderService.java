package com.eminetekcan.orderservice.service;

import com.eminetekcan.orderservice.dto.OrderDto;

public interface OrderService {

    public OrderDto placeOrder(OrderDto orderDto);
}
