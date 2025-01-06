package com.example.the_drone.exceptions;

public class WeightLimitExceededException extends RuntimeException {
    private int statusCode;

    public WeightLimitExceededException() {
    }

    public WeightLimitExceededException(String message) {
        super(message);
    }

    public WeightLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeightLimitExceededException(Throwable cause) {
        super(cause);
    }

    public WeightLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public WeightLimitExceededException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
