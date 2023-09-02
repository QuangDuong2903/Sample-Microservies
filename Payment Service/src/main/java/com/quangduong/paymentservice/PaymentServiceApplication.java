package com.quangduong.paymentservice;

import com.quangduong.paymentservice.entity.Payment;
import com.quangduong.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication implements CommandLineRunner {

    @Autowired
    private PaymentRepository paymentRepository;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        paymentRepository.save(Payment.builder()
//                .userId(1L)
//                .balance(100L)
//                .build());
    }
}
