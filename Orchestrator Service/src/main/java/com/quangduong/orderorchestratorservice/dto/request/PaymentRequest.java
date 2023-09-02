package com.quangduong.orderorchestratorservice.dto.request;

public record PaymentRequest(
        Long orderId,
        Long userId,
        Long price
) {}
