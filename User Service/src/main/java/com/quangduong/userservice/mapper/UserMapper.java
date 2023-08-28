package com.quangduong.userservice.mapper;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.entity.User;
import com.quangduong.userservice.repository.RoleRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public abstract class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeanMapping(qualifiedByName = "setRoleForUser")
    public abstract User createUserRequestToEntity(CreateUserRequest request);

    public abstract UserDTO userToUserDTO(User user);

    @AfterMapping
    @Named("setRoleForUser")
    public void setRoleForUser(CreateUserRequest request, @MappingTarget User user) {
        user.setPassword(passwordEncoder.encode(request.password()));
        user.getRoles().add(roleRepository.findOneByCode("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Not found ROLE_USER")));
    }

}
