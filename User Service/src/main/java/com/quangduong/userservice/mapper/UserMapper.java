package com.quangduong.userservice.mapper;

import com.quangduong.exceptionhandler.exception.ResourceNotFoundException;
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
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .roles(Set.of(roleRepository.findOneByCode("ROLE_USER")
                        .orElseThrow(() -> new ResourceNotFoundException("Not found ROLE_USER")))
                )
                .build();
    }

    public UserDTO toDTO(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getRoles().stream().map(Role::getCode).toList()
        );
    }
}
