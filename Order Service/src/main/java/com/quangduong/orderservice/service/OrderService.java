package com.quangduong.orderservice.service;

import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.saga.createorder.CreateOrderSagaData;

public interface OrderService {

    Order createOrder(CreateOrderSagaData data);

    void approveOrder(Long id);

    void rejectOrder(Long id);

}
