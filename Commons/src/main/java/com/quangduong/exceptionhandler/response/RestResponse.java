package com.quangduong.exceptionhandler.response;

public record RestResponse<T>(
        int status,
        T data
) {}
