package com.quangduong.orderorchestratorservice.eventhandlers;

import com.quangduong.orderorchestratorservice.client.InventoryClient;
import com.quangduong.orderorchestratorservice.client.PaymentClient;
import com.quangduong.orderorchestratorservice.dto.request.InventoryRequest;
import com.quangduong.orderorchestratorservice.dto.request.OrchestratorRequest;
import com.quangduong.orderorchestratorservice.dto.request.PaymentRequest;
import com.quangduong.orderorchestratorservice.dto.response.InventoryResponse;
import com.quangduong.orderorchestratorservice.dto.response.OrchestratorResponse;
import com.quangduong.orderorchestratorservice.dto.response.PaymentResponse;
import com.quangduong.orderorchestratorservice.dto.response.RestResponse;
import com.quangduong.orderorchestratorservice.enumeration.InventoryStatus;
import com.quangduong.orderorchestratorservice.enumeration.OrderStatus;
import com.quangduong.orderorchestratorservice.enumeration.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class OrderEventHandler {

    private final StreamBridge streamBridge;

    private final InventoryClient inventoryClient;

    private final PaymentClient paymentClient;

    @Bean
    public Consumer<OrchestratorRequest> handleCreateOrder() {
        AtomicReference<ResponseEntity<RestResponse<InventoryResponse>>> inventoryResponse = new AtomicReference<>();
        AtomicReference<ResponseEntity<RestResponse<PaymentResponse>>> paymentResponse = new AtomicReference<>();
        return request -> {
            CompletableFuture<Boolean> inventory = CompletableFuture.supplyAsync(() -> {
                ResponseEntity<RestResponse<InventoryResponse>> response =
                        inventoryClient.deduct(new InventoryRequest(
                                request.orderId(),
                                request.userId(),
                                request.productId()
                        ));
                inventoryResponse.set(response);
                return response.getStatusCode().is2xxSuccessful() && response.hasBody()
                        && Objects.requireNonNull(response.getBody()).data()
                        .status().equals(InventoryStatus.AVAILABLE);
            });
            CompletableFuture<Boolean> payment = CompletableFuture.supplyAsync(() -> {
                ResponseEntity<RestResponse<PaymentResponse>> response =
                        paymentClient.debit(new PaymentRequest(
                                request.orderId(),
                                request.userId(),
                                request.price()
                        ));
                paymentResponse.set(response);
                return response.getStatusCode().is2xxSuccessful() && response.hasBody()
                        && Objects.requireNonNull(response.getBody()).data()
                        .status().equals(PaymentStatus.APPROVED);
            });
            CompletableFuture<Void> combineResult = CompletableFuture.allOf(inventory, payment)
                    .thenApply(v -> Stream.of(inventory, payment).map(CompletableFuture::join).toList())
                    .thenAccept(l -> {
                        if (l.stream().anyMatch(i -> !i)) {
                            if (!inventoryResponse.get().getBody().data().status().equals(InventoryStatus.UNAVAILABLE))
                                inventoryClient.add(new InventoryRequest(
                                        request.orderId(),
                                        request.userId(),
                                        request.productId()
                                ));
                            if (!paymentResponse.get().getBody().data().status().equals(PaymentStatus.REJECTED))
                                paymentClient.credit(new PaymentRequest(
                                        request.orderId(),
                                        request.userId(),
                                        request.price()
                                ));
                            streamBridge.send("update-order-topic",
                                    new OrchestratorResponse(request.orderId(), OrderStatus.CANCELED));
                        } else
                            streamBridge.send("update-order-topic",
                                    new OrchestratorResponse(request.orderId(), OrderStatus.COMPLETED));
                    });
            try {
                combineResult.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
