package prismify.auth.infrastructure.controllers.dto.responses;

public record ApiError(
        String code,
        String message,
        String traceId) {
    public static ApiError of(String code, String message) {
        return new ApiError(code, message, null);
    }

    public static ApiError of(String code, String message, String traceId) {
        return new ApiError(code, message, traceId);
    }
}
