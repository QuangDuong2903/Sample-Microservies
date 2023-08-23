package com.quangduong.postservice.dto.response;

public record CreatePostResponse(
        Long id,
        String title,
        String content
) {}
