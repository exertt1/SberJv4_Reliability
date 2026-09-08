package com.app.sberjv4_reliabilty2.app.web.controller;

import com.app.sberjv4_reliabilty2.app.core.exceptions.RateLimiterExceedException;
import com.app.sberjv4_reliabilty2.app.dto.ErrorResponse;
import com.app.sberjv4_reliabilty2.app.dto.ReadTimeoutException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimiterExceedException.class)
    public ResponseEntity<ErrorResponse> handleRateLimiter(HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                429,
                "Too Many Requests",
                "Превышен лимит запросов, попробуйте позже...",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(429)
                .body(response);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<ErrorResponse> handleRateLimiterAddition(ServletServerHttpRequest request) {
        ErrorResponse response = new ErrorResponse(
                429,
                "Too Many Requests",
                "Превышен лимит запросов, попробуйте позже...",
                request.getURI().toString()
        );

        return ResponseEntity
                .status(429)
                .body(response);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ReadTimeoutException> handleTimeoutException() {
        ReadTimeoutException exception = new ReadTimeoutException(
                HttpStatus.GATEWAY_TIMEOUT.value(),
                "Сервер-поставщик нагружен, попробуйте ещё раз немного позже..."
        );

        return ResponseEntity.internalServerError().body(exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Bad request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(response);
    }
}
