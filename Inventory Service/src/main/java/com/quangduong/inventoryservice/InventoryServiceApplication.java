package com.quangduong.inventoryservice;

import com.quangduong.inventoryservice.entity.Inventory;
import com.quangduong.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryServiceApplication implements CommandLineRunner {

    @Autowired
    private InventoryRepository inventoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        inventoryRepository.save(Inventory.builder()
//                .productId(1L)
//                .quantity(2L)
//                .build());
    }
}
