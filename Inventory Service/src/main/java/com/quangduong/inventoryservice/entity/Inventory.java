package com.quangduong.inventoryservice.entity;

import com.quangduong.commons.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends BaseEntity {

    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

}
