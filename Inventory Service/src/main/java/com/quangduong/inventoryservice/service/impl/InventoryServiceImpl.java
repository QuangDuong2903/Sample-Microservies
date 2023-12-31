package com.quangduong.inventoryservice.service.impl;

import com.quangduong.inventoryservice.entity.Inventory;
import com.quangduong.inventoryservice.repository.InventoryRepository;
import com.quangduong.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void subtractQuantity(Long productId) {
        Inventory inventory = inventoryRepository.findOneByProductId(productId)
                .orElseThrow();
        if (inventory.getQuantity() > 0) {
            inventory.setQuantity(inventory.getQuantity() - 1);
            inventoryRepository.save(inventory);
        }
        else
            throw new RuntimeException();
    }

    @Override
    public void restoreQuantity(Long productId) {
        Inventory inventory = inventoryRepository.findOneByProductId(productId)
                .orElseThrow();
        inventory.setQuantity(inventory.getQuantity() + 1);
        inventoryRepository.save(inventory);
    }
}
