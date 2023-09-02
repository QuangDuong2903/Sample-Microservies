package com.quangduong.orderorchestratorservice.dto.request;

public record InventoryRequest(
        Long orderId,
        Long userId,
        Long productId
) {}
