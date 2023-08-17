package com.quangduong.userservice;

import com.quangduong.userservice.entity.Role;
import com.quangduong.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleRepository.save(Role.builder()
						.code("ROLE_ADMIN")
						.name("Admin")
				.build());
		roleRepository.save(Role.builder()
				.code("ROLE_USER")
				.name("User")
				.build());
	}
}
