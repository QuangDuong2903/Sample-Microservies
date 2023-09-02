package com.quangduong.paymentservice.service.impl;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.commons.response.RestResponse;
import com.quangduong.paymentservice.dto.request.PaymentRequest;
import com.quangduong.paymentservice.dto.response.PaymentResponse;
import com.quangduong.paymentservice.entity.Payment;
import com.quangduong.paymentservice.enumeration.PaymentStatus;
import com.quangduong.paymentservice.repository.PaymentRepository;
import com.quangduong.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public RestResponse<PaymentResponse> debit(PaymentRequest request) {
        Payment payment = paymentRepository.findOneByUserId(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found payment"));
        if (payment.getBalance() >= request.price()) {
            payment.setBalance(payment.getBalance() - request.price());
            paymentRepository.save(payment);
            return RestResponse.ok(new PaymentResponse(
                    request.userId(),
                    request.orderId(),
                    request.price(),
                    PaymentStatus.APPROVED
            ));
        }
        return RestResponse.ok(new PaymentResponse(
                request.userId(),
                request.orderId(),
                request.price(),
                PaymentStatus.REJECTED
        ));
    }

    @Override
    public void credit(PaymentRequest request) {
        Payment payment = paymentRepository.findOneByUserId(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found payment"));
        payment.setBalance(payment.getBalance() + request.price());
        paymentRepository.save(payment);
    }
}
