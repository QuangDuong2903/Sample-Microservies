package com.quangduong.postservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
        @NotBlank
        String title,
        @NotBlank
        String content
) {}
