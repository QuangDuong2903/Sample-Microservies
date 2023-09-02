package com.quangduong.orderservice.dto.response;

import com.quangduong.orderservice.enumeration.OrderStatus;

public record OrchestratorResponse(
        Long orderId,
        OrderStatus status
) {}
