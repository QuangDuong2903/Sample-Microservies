package com.quangduong.orderservice.dto.request;

public record OrchestratorRequest(
        Long orderId,
        Long userId,
        Long productId,
        Long price
) {}
