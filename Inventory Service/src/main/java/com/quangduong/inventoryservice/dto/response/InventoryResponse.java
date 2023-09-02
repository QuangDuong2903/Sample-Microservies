package com.quangduong.inventoryservice.dto.response;

import com.quangduong.inventoryservice.enumeration.InventoryStatus;

public record InventoryResponse(
        Long userId,
        Long productId,
        Long orderId,
        InventoryStatus status
) {}
