package com.quangduong.orderservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.orderservice.dto.request.CreateOrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;
import com.quangduong.orderservice.saga.OrderSagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderSagaService orderSagaService;

    @PostMapping
    public ResponseEntity<RestResponse<OrderResponse>> createOrder(@RequestBody CreateOrderRequest request) {
        RestResponse<OrderResponse> response = orderSagaService.createOrder(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

}
