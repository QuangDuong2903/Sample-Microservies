package com.quangduong.orderservice.dto.response;

import com.quangduong.orderservice.enumeration.OrderStatus;

public record OrderResponse(
        Long id,
        Long userId,
        Long productId,
        Long price,
        OrderStatus status

) {}
