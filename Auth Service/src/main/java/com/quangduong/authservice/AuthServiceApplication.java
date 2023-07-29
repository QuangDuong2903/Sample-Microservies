package com.quangduong.authservice;

import com.quangduong.authservice.entity.Role;
import com.quangduong.authservice.entity.User;
import com.quangduong.authservice.repository.RoleRepository;
import com.quangduong.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = Role.builder()
				.name("admin")
				.code("ROLE_ADMIN")
				.build();
		adminRole = roleRepository.save(adminRole);
		Role userRole = Role.builder()
				.name("user")
				.code("ROLE_USER")
				.build();
		userRole = roleRepository.save(userRole);
		User user = User.builder()
				.username("quangduong")
				.password(new BCryptPasswordEncoder().encode("292003"))
				.roles(Set.of(adminRole, userRole))
				.build();
		userRepository.save(user);
	}
}
