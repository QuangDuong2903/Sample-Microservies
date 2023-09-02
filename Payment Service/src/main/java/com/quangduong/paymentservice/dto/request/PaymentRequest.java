package com.quangduong.paymentservice.dto.request;

public record PaymentRequest(
        Long orderId,
        Long userId,
        Long price
) {}
