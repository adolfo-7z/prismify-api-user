package prismify.user.infrastructure.controllers.dto.responses;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ApiResponse<T>(
        String status,
        T data,
        ApiError error,
        Map<String, Object> meta) {
    private static final String SUCESS_STATUS = "success";

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(SUCESS_STATUS, data, null, null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(SUCESS_STATUS, data, null, null));
    }

    public static <T> ApiResponse<T> paged(T data, Map<String, Object> meta) {
        return new ApiResponse<>(SUCESS_STATUS, data, null, meta);
    }

    public static <T> ApiResponse<T> fail(ApiError error) {
        return new ApiResponse<>("error", null, error, null);
    }
}
