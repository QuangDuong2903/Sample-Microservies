package com.quangduong.userservice.service.impl;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.exception.UsernameAlreadyExistException;
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
    public RestResponse<List<UserDTO>> getAllUser() {
        return RestResponse.ok(userRepository.findAll().stream()
                .map(userMapper::userToUserDTO)
                .toList());
    }

    @Override
    public RestResponse<UserDTO> createUser(CreateUserRequest dto) {
        if (userRepository.findOneByUsername(dto.username()).isPresent())
            throw new UsernameAlreadyExistException("User with username: " + dto.username() + " already exist");
        return RestResponse.created(userMapper
                .userToUserDTO(userRepository.save(userMapper.createUserRequestToEntity(dto))));
    }

}
