package com.punnawat.backend.expections.handler;

import com.punnawat.backend.api.response.ApiResponse;
import com.punnawat.backend.expections.BaseException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // for the JwtException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Access Denied");
        response.setStatus(403);
        return ResponseEntity
                .status(403)
                .body(new ApiResponse<>("failed",response));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAuthException(AuthenticationException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Unauthorized: Token is missing or invalid.");
        response.setStatus(401);
        return ResponseEntity.status(401).body(new ApiResponse<>("failed", response));
    }


    // for the inside of BaseException and its subclasses
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBaseException(BaseException e) {
        ErrorResponse response = new ErrorResponse();
        response.setError(e.getMessage());
        response.setStatus(e.getStatus().value());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ApiResponse<>("failed",response));
    }

    @Data
    public static class ErrorResponse {
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String error;
    }

}
