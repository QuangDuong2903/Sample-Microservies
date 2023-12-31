package com.quangduong.orderservice.dto.request;

public record CreateOrderRequest(
        Long productId,
        Long price
) {}
