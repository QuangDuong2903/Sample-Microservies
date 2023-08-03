package com.quangduong.userservice.service;

import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    UserDTO createUser(CreateUserRequest dto);
}
