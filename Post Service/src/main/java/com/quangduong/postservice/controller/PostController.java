package com.quangduong.postservice.controller;

import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.PostDTO;
import com.quangduong.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("{id}")
    public ResponseEntity<RestResponse<PostDTO>> getOnePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getOnePost(id));
    }

    @PostMapping
    public ResponseEntity<RestResponse<PostDTO>> createPost(@RequestBody @Valid CreatePostRequest request) {
        RestResponse<PostDTO> response = postService.createPost(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.data().id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

}
