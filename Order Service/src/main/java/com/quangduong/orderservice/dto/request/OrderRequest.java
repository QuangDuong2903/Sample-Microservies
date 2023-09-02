package com.quangduong.orderservice.dto.request;

public record OrderRequest(
        Long productId,
        Long price
) {}
