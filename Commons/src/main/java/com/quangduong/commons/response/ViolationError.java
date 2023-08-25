package com.quangduong.commons.response;

import java.util.List;

public record ViolationError(
    String field,
    List<String> messages
) {}
