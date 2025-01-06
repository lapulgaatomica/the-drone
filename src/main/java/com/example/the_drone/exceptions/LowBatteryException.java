package com.example.the_drone.exceptions;

public class LowBatteryException extends RuntimeException {
    private int statusCode;

    public LowBatteryException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public LowBatteryException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public LowBatteryException(String message) {
        super(message);
    }

    public LowBatteryException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public LowBatteryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }
}
