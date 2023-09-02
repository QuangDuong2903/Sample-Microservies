package com.quangduong.paymentservice.repository;

import com.quangduong.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findOneByUserId(Long userId);

}
