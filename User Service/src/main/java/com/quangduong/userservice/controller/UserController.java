package com.quangduong.userservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.userservice.dto.request.CreateUserRequest;
import com.quangduong.userservice.dto.response.UserDTO;
import com.quangduong.userservice.service.UserService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @GetMapping("circuit-breaker")
    @CircuitBreaker(name = "post", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "post")
    @Retry(name = "post")
    @Bulkhead(name = "post", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<String>> test() {
        return CompletableFuture.supplyAsync(() -> {
            userService.test();
           return ResponseEntity.ok("OKE");
        });
    }

    public CompletableFuture<ResponseEntity<String>> fallbackMethod(RuntimeException exception) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.internalServerError().body("Some thing went wrong"));
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
