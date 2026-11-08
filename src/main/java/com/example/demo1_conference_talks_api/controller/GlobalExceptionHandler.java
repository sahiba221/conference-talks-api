package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.ApiError;
import com.example.demo1_conference_talks_api.exception.BusinessRuleException;
import com.example.demo1_conference_talks_api.exception.DuplicateResourceException;
import com.example.demo1_conference_talks_api.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex,
                                                   HttpServletRequest request){
        log.warn("Resource not found: {} ", ex.getMessage());
        ApiError apiError = ApiError.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity <ApiError> handleDublicate(DuplicateResourceException ex,
                                                     HttpServletRequest request){
        log.warn("Dublicate resources: {} ", ex.getMessage());
        ApiError apiError = ApiError.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity <ApiError> handleException(BusinessRuleException ex,
                                                     HttpServletRequest request){
        log.warn("Business rule violation: {} ", ex.getMessage());
        ApiError apiError =  ApiError.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
    }
}
