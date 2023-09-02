package com.quangduong.inventoryservice.service;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.inventoryservice.dto.request.InventoryRequest;
import com.quangduong.inventoryservice.dto.response.InventoryResponse;

public interface InventoryService {

    RestResponse<InventoryResponse> deduct(InventoryRequest request);

    void add(InventoryRequest request);

}
