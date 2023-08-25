package com.quangduong.userservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.dto.response.UserDetailsResponse;
import com.quangduong.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final StreamBridge streamBridge;

    @GetMapping
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("details")
    public ResponseEntity<RestResponse<UserDetailsResponse>> getUserDetails(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserDetails(username));
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestResponse<UserDTO>> createUser(@RequestBody @Valid CreateUserRequest request) {
        RestResponse<UserDTO> response = userService.createUser(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("{message}")
    public ResponseEntity<String> sendMessages(@PathVariable String message) {
        streamBridge.send("notification-topic", new Data("message", message));
        return ResponseEntity.ok("Sending message: " + message);
    }

    record Data(String title, String message) {}

}
