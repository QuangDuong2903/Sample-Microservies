package com.quangduong.orderorchestratorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderOrchestratorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderOrchestratorServiceApplication.class, args);
	}

}
