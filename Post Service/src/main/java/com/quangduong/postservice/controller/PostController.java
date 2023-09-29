package com.quangduong.postservice.controller;

import com.quangduong.commons.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.PostDTO;
import com.quangduong.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<Void> testApi() throws InterruptedException {
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");
        return ResponseEntity.ok().build();
    }

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
