package com.quangduong.orderservice.eventhandlers;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.orderservice.dto.request.OrchestratorRequest;
import com.quangduong.orderservice.dto.response.OrchestratorResponse;
import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.mapper.OrderMapper;
import com.quangduong.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderRepository orderRepository;

    @Bean
    public Consumer<OrchestratorResponse> handleUpdateOrder() {
        return response -> {
            Order order = orderRepository.findById(response.orderId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Not found order with id: " + response.orderId()));
            order.setStatus(response.status());
            orderRepository.save(order);
        };
    }

}
