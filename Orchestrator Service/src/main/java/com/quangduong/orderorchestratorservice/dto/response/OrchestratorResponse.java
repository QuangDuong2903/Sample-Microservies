package com.quangduong.orderorchestratorservice.dto.response;

import com.quangduong.orderorchestratorservice.enumeration.OrderStatus;

public record OrchestratorResponse(
        Long orderId,
        OrderStatus status
) {}
