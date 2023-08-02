package com.quangduong.userservice.service;

import com.quangduong.userservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();
}
