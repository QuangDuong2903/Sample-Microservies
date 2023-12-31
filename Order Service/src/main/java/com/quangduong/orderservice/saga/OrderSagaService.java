package com.quangduong.orderservice.saga;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.commons.response.RestResponse;
import com.quangduong.orderservice.dto.request.CreateOrderRequest;
import com.quangduong.orderservice.dto.response.OrderResponse;
import com.quangduong.orderservice.enumeration.OrderStatus;
import com.quangduong.orderservice.mapper.OrderMapper;
import com.quangduong.orderservice.repository.OrderRepository;
import com.quangduong.orderservice.saga.createorder.CreateOrderSaga;
import com.quangduong.orderservice.saga.createorder.CreateOrderSagaData;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSagaService {

    private final OrderRepository orderRepository;
    private final SagaInstanceFactory sagaInstanceFactory;
    private final CreateOrderSaga createOrderSaga;
    private final OrderMapper orderMapper;

    public RestResponse<OrderResponse> createOrder(CreateOrderRequest request) {
        CreateOrderSagaData data = CreateOrderSagaData.builder()
                .productId(request.productId())
                .price(request.price())
                .status(OrderStatus.CREATED)
                .build();
        sagaInstanceFactory.create(createOrderSaga, data);
        return RestResponse.ok(orderMapper.orderToOrderResponse(
                orderRepository.findById(data.getOrderId())
                        .orElseThrow(() -> new ResourceNotFoundException("Not found order"))
        ));
    }

}
