package com.quangduong.inventoryservice.dto.request;

public record InventoryRequest(
        Long userId,
        Long productId,
        Long orderId
) {}
