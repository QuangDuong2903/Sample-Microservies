package com.quangduong.orderorchestratorservice.client;


import com.quangduong.orderorchestratorservice.dto.request.InventoryRequest;
import com.quangduong.orderorchestratorservice.dto.response.InventoryResponse;
import com.quangduong.orderorchestratorservice.dto.response.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE", url = "http://localhost:8084/inventories")
public interface InventoryClient {

    @PostMapping("deduct")
    ResponseEntity<RestResponse<InventoryResponse>> deduct(@RequestBody InventoryRequest request);

    @PostMapping("add")
    ResponseEntity<Void> add(@RequestBody InventoryRequest request);

}
