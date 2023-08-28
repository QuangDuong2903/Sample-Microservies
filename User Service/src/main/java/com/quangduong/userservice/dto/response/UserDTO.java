package com.quangduong.userservice.dto.response;

import java.util.List;

public record UserDTO(
        Long id,
        String username,
        List<RoleDTO> roles
) {}
