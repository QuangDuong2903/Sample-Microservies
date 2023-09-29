package com.quangduong.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("POST-SERVICE")
public interface PostServiceClient {

    @GetMapping("posts")
    ResponseEntity<Void> test();

}
