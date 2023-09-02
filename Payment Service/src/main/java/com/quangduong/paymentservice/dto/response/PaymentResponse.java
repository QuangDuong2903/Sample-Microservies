package com.quangduong.paymentservice.dto.response;

import com.quangduong.paymentservice.enumeration.PaymentStatus;

public record PaymentResponse(
        Long orderId,
        Long userId,
        Long price,
        PaymentStatus status
) {}
