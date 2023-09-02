package com.quangduong.inventoryservice.repository;

import com.quangduong.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findOneByProductId(Long productId);

}
