package com.quangduong.orderservice.saga.createorder;

import com.quangduong.orderservice.entity.Order;
import com.quangduong.orderservice.saga.participant.InventoryServiceProxy;
import com.quangduong.orderservice.service.OrderService;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

    private final OrderService orderService;
    private final InventoryServiceProxy inventoryServiceProxy;

    private final SagaDefinition<CreateOrderSagaData> sagaDefinition =
            step()
                .invokeLocal(this::create)
                .withCompensation(this::reject)
            .step()
                .invokeParticipant(this::subtractQuantity)
                .withCompensation(this::restoreQuantity)
            .step()
                .invokeLocal(this::approve)
            .build();

    @Override
    public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
        return sagaDefinition;
    }

    private void create(CreateOrderSagaData data) {
        Order order = orderService.createOrder(data);
        data.setOrderId(order.getId());
    }

    private void approve(CreateOrderSagaData data) {
        orderService.approveOrder(data.getOrderId());
    }

    private void reject(CreateOrderSagaData data) {
        orderService.rejectOrder(data.getOrderId());
    }

    private CommandWithDestination subtractQuantity(CreateOrderSagaData data) {
        return inventoryServiceProxy.subtractQuantity(data.getProductId());
    }

    private CommandWithDestination restoreQuantity(CreateOrderSagaData data) {
        return inventoryServiceProxy.restoreQuantity(data.getProductId());
    }
}
