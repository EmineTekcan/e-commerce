package com.eminetekcan.orderservice.service.impl;

import com.eminetekcan.orderservice.dto.InventoryDto;
import com.eminetekcan.orderservice.dto.OrderDto;
import com.eminetekcan.orderservice.dto.OrderItemDto;
import com.eminetekcan.orderservice.model.Order;
import com.eminetekcan.orderservice.model.OrderItem;
import com.eminetekcan.orderservice.repository.OrderRepository;
import com.eminetekcan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public OrderDto placeOrder(OrderDto orderDto) {


        List<OrderItem> orderItems=orderDto.getOrderItemDtos().stream().map(this::orderItemDtoToOrderItem).toList();


        List<String> skuCodes=orderItems.stream().map(orderItem -> orderItem.getSkuCode()).toList();

        InventoryDto[] inventoryDtos=webClientBuilder.build().get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryDto[].class)
                .block();
        System.out.println(inventoryDtos.length);
        Boolean isInStockAll= Arrays.stream(inventoryDtos).allMatch(inventoryDto -> inventoryDto.getIsInStock()==true);
        if (inventoryDtos.length==skuCodes.size()){
            Order order=new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderItems);
            Order savedOrder=orderRepository.save(order);
            return itemToDto(savedOrder);
        }

        throw new RuntimeException("Product is not in stock !! ");
    }

    private OrderItem orderItemDtoToOrderItem(OrderItemDto orderItemDto){
        return OrderItem.builder()
                .skuCode(orderItemDto.getSkuCode())
                .price(orderItemDto.getPrice())
                .quantity(orderItemDto.getQuantity())
                .build();
    }
    private OrderItemDto mapToDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .skuCode(orderItem.getSkuCode())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }
    private OrderDto itemToDto(Order order){
        List<OrderItemDto> orderItemDtos=order.getOrderItems().stream().map(this::mapToDto).toList();
        return OrderDto.builder()
                .orderItemDtos(orderItemDtos)
                .build();
    }
}
