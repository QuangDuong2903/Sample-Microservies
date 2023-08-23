package com.quangduong.postservice.mapper;

import com.quangduong.exceptionhandler.security.UserDetailsImpl;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.CreatePostResponse;
import com.quangduong.postservice.entity.Post;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest dto) {
        return new Post(
                dto.title(),
                dto.content(),
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
