package com.quangduong.postservice.service.impl;

import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;
import com.quangduong.postservice.mapper.PostMapper;
import com.quangduong.postservice.repository.PostRepository;
import com.quangduong.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public CreatePostResponse createPost(CreatePostRequest dto) {
        return postMapper.toCreatePostResponse(postRepository.save(postMapper.toEntity(dto)));
    }
}
