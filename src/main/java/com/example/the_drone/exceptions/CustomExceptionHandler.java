package com.example.the_drone.exceptions;

import com.example.the_drone.dtos.TheDroneResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.StringJoiner;

@RestControllerAdvice
public class CustomExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public TheDroneResponse handleGeneralException(Exception e, HttpServletRequest request) {
        logger.info("Unknown server error", e);
        return new TheDroneResponse(false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage(), null);
    }

    @ExceptionHandler(LowBatteryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TheDroneResponse handleLowBatteryException(LowBatteryException e, HttpServletRequest request) {
        logger.info("drone battery too low for loading", e);
        return new TheDroneResponse(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TheDroneResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        logger.info("drone battery too low for loading", e);
        StringJoiner stringJoiner = new StringJoiner("\n");
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        for (String error : errors) {
            stringJoiner.add(error);
        }

        return new TheDroneResponse(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), stringJoiner.toString(), null);
    }

    @ExceptionHandler(WeightLimitExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TheDroneResponse handleWeightLimitExceededException(WeightLimitExceededException e, HttpServletRequest request) {
        logger.info("drone weight limit exceeded", e);
        return new TheDroneResponse(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public TheDroneResponse handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        logger.info("entity not found", e);
        return new TheDroneResponse(false, String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage(), null);
    }
}
