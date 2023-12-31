package com.quangduong.orderservice.mapper;

import com.quangduong.orderservice.dto.request.CreateOrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;
import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.repository.OrderRepository;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public interface OrderMapper {

    Order createOrderRequestToOrder(CreateOrderRequest request);

    OrderResponse orderToOrderResponse(Order order);

}
