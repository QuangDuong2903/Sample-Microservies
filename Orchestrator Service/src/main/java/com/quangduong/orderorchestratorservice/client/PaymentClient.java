package com.quangduong.orderorchestratorservice.client;

import com.quangduong.orderorchestratorservice.dto.request.PaymentRequest;
import com.quangduong.orderorchestratorservice.dto.response.PaymentResponse;
import com.quangduong.orderorchestratorservice.dto.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8085/payment")
public interface PaymentClient {

    @PostMapping("debit")
    ResponseEntity<RestResponse<PaymentResponse>> debit(@RequestBody PaymentRequest request);

    @PostMapping("credit")
    ResponseEntity<Void> credit(@RequestBody PaymentRequest request);

}
