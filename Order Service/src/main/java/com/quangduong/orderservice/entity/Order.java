package com.quangduong.orderservice.entity;

import com.quangduong.commons.jpa.entity.BaseEntity;
import com.quangduong.orderservice.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
