package com.quangduong.inventoryservice.service;

public interface InventoryService {

    void subtractQuantity(Long productId);

    void restoreQuantity(Long productId);

}
