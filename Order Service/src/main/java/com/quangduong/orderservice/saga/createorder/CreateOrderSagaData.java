package com.quangduong.orderservice.saga.createorder;

import com.quangduong.orderservice.enumeration.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderSagaData {
    private Long orderId;
    private Long productId;
    private Long price;
    private OrderStatus status;
}
