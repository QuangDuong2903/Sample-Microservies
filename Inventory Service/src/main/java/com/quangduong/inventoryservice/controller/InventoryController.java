package com.quangduong.inventoryservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.inventoryservice.dto.request.InventoryRequest;
import com.quangduong.inventoryservice.dto.response.InventoryResponse;
import com.quangduong.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("deduct")
    public ResponseEntity<RestResponse<InventoryResponse>> deduct(@RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.deduct(request));
    }

    @PostMapping("add")
    public ResponseEntity<Void> add(@RequestBody InventoryRequest request) {
        inventoryService.add(request);
        return ResponseEntity.ok().build();
    }

}
