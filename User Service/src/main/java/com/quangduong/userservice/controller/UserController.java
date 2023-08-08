package com.quangduong.userservice.controller;

import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.dto.response.UserDetailsResponse;
import com.quangduong.userservice.security.UserDetailsImpl;
import com.quangduong.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserDetails(username));
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid CreateUserRequest dto) {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

}
