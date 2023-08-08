package com.quangduong.postservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
