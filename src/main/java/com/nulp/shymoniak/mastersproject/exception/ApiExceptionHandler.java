package com.nulp.shymoniak.mastersproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiException filesException =
                new ApiException(ex.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(filesException, status);
    }
}
