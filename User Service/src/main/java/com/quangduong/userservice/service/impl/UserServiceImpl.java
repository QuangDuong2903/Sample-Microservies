package com.quangduong.userservice.service.impl;

import com.quangduong.userservice.dto.UserDTO;
import com.quangduong.userservice.entity.Role;
import com.quangduong.userservice.repository.UserRepository;
import com.quangduong.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream()
                .map(u -> new UserDTO(u.getUsername(),
                        u.getRoles().stream().map(Role::getCode).toList()))
                .toList();
    }
}
