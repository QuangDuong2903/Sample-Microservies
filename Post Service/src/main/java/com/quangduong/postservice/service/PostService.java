package com.quangduong.postservice.service;

import com.quangduong.exceptionhandler.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.PostDTO;

public interface PostService {

    RestResponse<PostDTO> createPost(CreatePostRequest dto);

    RestResponse<PostDTO> getOnePost(Long id);
}
