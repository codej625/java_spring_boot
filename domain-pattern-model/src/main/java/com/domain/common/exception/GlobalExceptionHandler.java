package com.domain.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 타임 아웃 관련 예외
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleSocketTimeoutException(SocketTimeoutException ex) {
        log.error("SocketTimeoutException error occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .status(HttpStatus.BAD_REQUEST.value())
                                                   .code("TIMEOUT_ERROR")
                                                   .message(ex.getMessage())
                                                   .errors(Map.of("Exception", "SocketTimeoutException"))
                                                   .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.REQUEST_TIMEOUT);
    }

    // 널 값 관련 예외
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException error occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .status(HttpStatus.BAD_REQUEST.value())
                                                   .code("NULL_ERROR")
                                                   .message(ex.getMessage())
                                                   .errors(Map.of("Exception", "NullPointerException"))
                                                   .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 비즈니스 로직 관련 예외
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.error("BusinessException error occurred: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .status(HttpStatus.BAD_REQUEST.value())
                                                   .code("BUSINESS_ERROR")
                                                   .message(ex.getMessage())
                                                   .errors(Map.of("Exception", "BusinessException"))
                                                   .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 데이터 검증 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("ValidationExceptions error occurred: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .status(HttpStatus.BAD_REQUEST.value())
                                                   .code("VALIDATION_ERROR")
                                                   .message("입력 데이터 검증 실패")
                                                   .errors(errors)
                                                   .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 커스텀 비즈니스 예외 클래스
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }

    // 에러 응답 DTO (Lombok 빌더 사용)
    @Getter
    @Builder
    public static class ErrorResponse {
        private int status;
        private String code;
        private String message;
        private Map<String, String> errors;
    }
}