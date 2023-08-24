package com.quangduong.postservice.dto.response;

public record PostDTO(
        Long id,
        String title,
        String content
) {}
