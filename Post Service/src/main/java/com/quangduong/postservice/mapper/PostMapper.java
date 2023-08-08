package com.quangduong.postservice.mapper;

import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;
import com.quangduong.postservice.entity.Post;
import com.quangduong.postservice.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest dto) {
        return new Post(
                dto.getTitle(),
                dto.getContent(),
                ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId()
        );
    }

    public CreatePostResponse toCreatePostResponse(Post entity) {
        return new CreatePostResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent()
        );
    }
}
