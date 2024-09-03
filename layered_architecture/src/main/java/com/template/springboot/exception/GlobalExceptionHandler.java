package com.template.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        // 전역 예외 처리 로직
        return new ResponseEntity<>("예외 -> " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
