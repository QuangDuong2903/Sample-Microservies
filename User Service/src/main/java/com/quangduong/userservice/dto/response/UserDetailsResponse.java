package com.quangduong.userservice.dto.response;

import java.util.List;

public record UserDetailsResponse(
        Long id,
        String username,
        String password,
        List<String> authorities
) {}
