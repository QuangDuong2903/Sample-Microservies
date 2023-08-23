package com.quangduong.postservice.service;

import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;

public interface PostService {

    RestResponse<CreatePostResponse> createPost(CreatePostRequest dto);
}
