package com.quangduong.orderorchestratorservice.dto.response;

import com.quangduong.orderorchestratorservice.enumeration.InventoryStatus;

public record InventoryResponse(
        Long userId,
        Long productId,
        Long orderId,
        InventoryStatus status
) {}
