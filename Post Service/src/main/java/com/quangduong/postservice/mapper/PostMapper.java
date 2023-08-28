package com.quangduong.postservice.mapper;

import com.quangduong.commons.security.UserDetailsImpl;
import com.quangduong.postservice.dto.request.CreatePostRequest;
import com.quangduong.postservice.dto.response.PostDTO;
import com.quangduong.postservice.entity.Post;
import org.mapstruct.*;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public interface PostMapper {

    @BeanMapping(qualifiedByName = "setUserIdForPost")
    Post createPostRequestToPost(CreatePostRequest request);

    PostDTO postToPostDTO(Post post);

    @AfterMapping
    @Named("setUserIdForPost")
    default void setUserIdForPost(@MappingTarget Post post) {
        post.setUserId(((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .getId());
    }

}
