package com.quangduong.postservice.client.user;

import com.quangduong.postservice.client.user.dto.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserClient {

    @GetMapping("users/details")
    ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String username);
}
