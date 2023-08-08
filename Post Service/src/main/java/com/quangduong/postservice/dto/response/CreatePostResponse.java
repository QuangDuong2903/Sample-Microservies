package com.quangduong.postservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostResponse {

    private Long id;

    private String title;

    private String content;
}
