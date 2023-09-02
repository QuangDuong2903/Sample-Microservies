package com.quangduong.orderservice.mapper;

import com.quangduong.commons.utils.SecurityUtils;
import com.quangduong.orderservice.dto.request.OrchestratorRequest;
import com.quangduong.orderservice.dto.request.OrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;
import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.enumeration.OrderStatus;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public abstract class OrderMapper {

    @Autowired
    private SecurityUtils securityUtils;

    @BeanMapping(qualifiedByName = "setStatusAndUserIdForOrder")
    public abstract Order orderRequestToOrder(OrderRequest request);

    public abstract OrderResponse orderToOrderResponse(Order order);

    @Mapping(source = "id", target = "orderId")
    public abstract OrchestratorRequest orderToOrchestratorRequest(Order order);

    @AfterMapping
    @Named("setStatusAndUserIdForOrder")
    public void setStatusAndUserIdForOrder(@MappingTarget Order order) {
        order.setUserId(securityUtils.getUserId());
        order.setStatus(OrderStatus.CREATED);
    }

}
