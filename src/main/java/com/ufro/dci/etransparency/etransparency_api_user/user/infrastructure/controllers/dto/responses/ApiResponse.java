package com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.dto.responses;

import java.util.Map;

public record ApiResponse<T>(
        String status,
        T data,
        ApiError error,
        Map<String, Object> meta) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("success", data, null, null);
    }

    public static <T> ApiResponse<T> paged(T data, Map<String, Object> meta) {
        return new ApiResponse<>("sucess", data, null, meta);
    }

    public static <T> ApiResponse<T> fail(ApiError error) {
        return new ApiResponse<>("error", null, error, null);
    }
}
