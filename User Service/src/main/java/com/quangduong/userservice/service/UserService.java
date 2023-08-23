package com.quangduong.userservice.service;

import com.quangduong.exceptionhandler.response.ListResponse;
import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.dto.response.UserDetailsResponse;

import java.util.List;

public interface UserService {

    RestResponse<List<UserDTO>> getAllUser();

    RestResponse<UserDTO> createUser(CreateUserRequest dto);

    RestResponse<UserDetailsResponse> getUserDetails(String username);
}
