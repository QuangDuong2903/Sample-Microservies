package com.quangduong.postservice.controller;

import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;
import com.quangduong.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<RestResponse<CreatePostResponse>> createPost(@RequestBody @Valid CreatePostRequest request) {
        RestResponse<CreatePostResponse> response = postService.createPost(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }
}
