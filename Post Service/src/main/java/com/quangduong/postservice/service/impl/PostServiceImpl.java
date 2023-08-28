package com.quangduong.postservice.service.impl;

import com.quangduong.commons.exception.ResourceNotFoundException;
import com.quangduong.commons.response.RestResponse;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.PostDTO;
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
    public RestResponse<PostDTO> createPost(CreatePostRequest dto) {
        return RestResponse.created(postMapper.postToPostDTO(postRepository
                .save(postMapper.createPostRequestToPost(dto))));
    }

    @Override
    public RestResponse<PostDTO> getOnePost(Long id) {
        return postRepository.findById(id)
                .map(postMapper::postToPostDTO)
                .map(RestResponse::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Not found post with id: " + id));
    }
}
