package com.quangduong.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsResponse {

    private Long id;

    private String username;

    private String password;

    private List<String> authorities;
}
