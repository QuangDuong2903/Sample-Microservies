package com.quangduong.paymentservice.service;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.paymentservice.dto.request.PaymentRequest;
import com.quangduong.paymentservice.dto.response.PaymentResponse;

public interface PaymentService {

    RestResponse<PaymentResponse> debit(PaymentRequest request);

    void credit(PaymentRequest request);

}
