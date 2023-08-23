package com.quangduong.userservice.service.impl;

import com.quangduong.exceptionhandler.exception.ResourceNotFoundException;
import com.quangduong.exceptionhandler.response.ListResponse;
import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.dto.response.UserDetailsResponse;
import com.quangduong.userservice.entity.Role;
import com.quangduong.userservice.entity.User;
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
                .map(userMapper::toDTO)
                .toList());
    }

    @Override
    public RestResponse<UserDTO> createUser(CreateUserRequest dto) {
        if (userRepository.findOneByUsername(dto.username()).isPresent())
            throw new UsernameAlreadyExistException("User with username: " + dto.username() + " already exist");
        return RestResponse.created(userMapper.toDTO(userRepository.save(userMapper.toEntity(dto))));
    }

    @Override
    public RestResponse<UserDetailsResponse> getUserDetails(String username) {
        User user = userRepository.findOneByUsernameWithRoles(username)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with username: " + username));
        return RestResponse.ok(new UserDetailsResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getCode).toList()
        ));
    }
}
