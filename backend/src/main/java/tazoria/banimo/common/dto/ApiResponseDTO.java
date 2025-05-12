package tazoria.banimo.common.dto;

import lombok.*;

/**
 * API 공통 Response 객체
 */
@Getter
@Builder
@AllArgsConstructor
public class ApiResponseDTO<T> {

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_FAIL = "fail";
    private static final String STATUS_ERROR = "error";

    private String status;
    private T data;
    private ApiError errors;

    /**
     * 성공 응답
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResponseDTO<T> success(T data) {
        return ApiResponseDTO.<T>builder()
                .status(STATUS_SUCCESS)
                .data(data)
                .errors(null)
                .build();
    }

    /**
     * 실패 응답
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ApiResponseDTO<T> fail(String message) {
        return ApiResponseDTO.<T>builder()
                .status(STATUS_FAIL)
                .data(null)
                .errors(ApiError.builder()
                        .code("-1")
                        .message(message)
                        .validations(null)
                        .build()
                ).build();
    }

    /**
     * 에러 응답
     * @param error
     * @return
     * @param <T>
     */
    public static <T> ApiResponseDTO<T> error(ApiError error) {
        return ApiResponseDTO.<T>builder()
                .status(STATUS_ERROR)
                .data(null)
                .errors(error)
                .build();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ApiError {
        @Builder.Default
        private String code = "-99";
        private String message;
        private Object validations;
    }
}
