package com.quangduong.orderservice.service;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.orderservice.dto.request.OrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;

public interface OrderService {

    RestResponse<OrderResponse> createOrder(OrderRequest request);

}
