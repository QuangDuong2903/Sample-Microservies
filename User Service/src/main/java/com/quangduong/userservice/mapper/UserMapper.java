package com.quangduong.userservice.mapper;

import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.entity.Role;
import com.quangduong.userservice.entity.User;
import com.quangduong.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public User toEntity(CreateUserRequest dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(roleRepository.findOneByCode("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Not found ROLE_USER")))
                )
                .build();
    }

    public UserDTO toDTO(User entity) {
        return new UserDTO(
                entity.getUsername(),
                entity.getRoles().stream().map(Role::getCode).toList()
        );
    }
}
