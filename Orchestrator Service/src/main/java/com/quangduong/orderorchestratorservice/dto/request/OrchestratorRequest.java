package com.quangduong.orderorchestratorservice.dto.request;

public record OrchestratorRequest(
        Long orderId,
        Long userId,
        Long productId,
        Long price
) {}
