package com.app.sberjv4_reliabilty2.app.core.exceptions;

public class RateLimiterExceedException extends RuntimeException {
    public RateLimiterExceedException(String message) {
        super(message);
    }
}
