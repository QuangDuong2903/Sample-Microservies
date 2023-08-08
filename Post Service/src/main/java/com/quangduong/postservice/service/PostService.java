package com.quangduong.postservice.service;

import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;

public interface PostService {

    CreatePostResponse createPost(CreatePostRequest dto);
}
