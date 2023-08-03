package com.quangduong.userservice.service.impl;

import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.mapper.UserMapper;
import com.quangduong.userservice.repository.UserRepository;
import com.quangduong.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO createUser(CreateUserRequest dto) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(dto)));
    }
}
