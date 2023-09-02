package com.quangduong.paymentservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.paymentservice.dto.request.PaymentRequest;
import com.quangduong.paymentservice.dto.response.PaymentResponse;
import com.quangduong.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("debit")
    public ResponseEntity<RestResponse<PaymentResponse>> debit(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.debit(request));
    }

    @PostMapping("credit")
    public ResponseEntity<Void> credit(@RequestBody PaymentRequest request) {
        paymentService.credit(request);
        return ResponseEntity.ok().build();
    }

}
