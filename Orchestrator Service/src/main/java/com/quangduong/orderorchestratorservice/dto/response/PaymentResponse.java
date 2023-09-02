package com.quangduong.orderorchestratorservice.dto.response;

import com.quangduong.orderorchestratorservice.enumeration.PaymentStatus;

public record PaymentResponse(
        Long orderId,
        Long userId,
        Long price,
        PaymentStatus status
) {}
