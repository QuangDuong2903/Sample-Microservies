package com.quangduong.orderservice.service.impl;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.enumeration.OrderStatus;
import com.quangduong.orderservice.repository.OrderRepository;
import com.quangduong.orderservice.saga.createorder.CreateOrderSagaData;
import com.quangduong.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(CreateOrderSagaData data) {
        Order order = Order.builder()
                .productId(data.getProductId())
                .price(data.getPrice())
                .status(data.getStatus())
                .build();
        orderRepository.save(order);
        return order;
    }

    @Override
    public void approveOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found order"));
        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);
    }

    @Override
    public void rejectOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found order"));
        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);
    }
}
