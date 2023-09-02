package com.quangduong.inventoryservice.service.impl;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.commons.response.RestResponse;
import com.quangduong.inventoryservice.dto.request.InventoryRequest;
import com.quangduong.inventoryservice.dto.response.InventoryResponse;
import com.quangduong.inventoryservice.entity.Inventory;
import com.quangduong.inventoryservice.enumeration.InventoryStatus;
import com.quangduong.inventoryservice.repository.InventoryRepository;
import com.quangduong.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public RestResponse<InventoryResponse> deduct(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findOneByProductId(request.productId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Not found product with id: " + request.productId()));
        if (inventory.getQuantity() > 0) {
            inventory.setQuantity(inventory.getQuantity() - 1);
            inventoryRepository.save(inventory);
            return RestResponse.ok(new InventoryResponse(
                    request.userId(),
                    request.productId(),
                    request.orderId(),
                    InventoryStatus.AVAILABLE
            ));
        }
        return RestResponse.ok(new InventoryResponse(
                request.userId(),
                request.productId(),
                request.orderId(),
                InventoryStatus.UNAVAILABLE
        ));
    }

    @Override
    public void add(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findOneByProductId(request.productId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Not found product with id: " + request.productId()));
        inventory.setQuantity(inventory.getQuantity() + 1);
        inventoryRepository.save(inventory);
    }
}
