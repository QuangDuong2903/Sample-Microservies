package com.quangduong.orderservice.service.impl;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.orderservice.dto.request.OrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;
import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.mapper.OrderMapper;
import com.quangduong.orderservice.repository.OrderRepository;
import com.quangduong.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final StreamBridge streamBridge;

    @Override
    public RestResponse<OrderResponse> createOrder(OrderRequest request) {
        Order order = orderRepository.save(orderMapper.orderRequestToOrder(request));
        streamBridge.send("create-order-topic", orderMapper.orderToOrchestratorRequest(order));
        return RestResponse.created(orderMapper.orderToOrderResponse(order));
    }
}
