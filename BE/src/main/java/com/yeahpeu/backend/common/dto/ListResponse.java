package com.yeahpeu.backend.common.dto;

import java.util.List;

public record ListResponse<T>(
        int count,
        List<T> data
) {
    public static <T> ListResponse<T> of(List<T> data) {
        return new ListResponse<T>(data.size(), data);
    }
}
