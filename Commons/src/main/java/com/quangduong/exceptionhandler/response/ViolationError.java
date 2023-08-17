package com.quangduong.exceptionhandler.response;

import java.util.List;

public record ViolationError(

    String field,
    List<String> messages
) {}
