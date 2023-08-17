package com.quangduong.exceptionhandler.response;

import java.util.List;

public record ListResponse<T>(
        int currentItemCount,
        int itemsPerPage,
        long totalItems,
        int pageIndex,
        int totalPages,
        List<T> items
) {}