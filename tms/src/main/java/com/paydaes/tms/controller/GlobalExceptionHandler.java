package com.paydaes.tms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public @ResponseBody ErrorResponse handleException(IllegalArgumentException ex) {
        return ErrorResponse.create(ex,HttpStatus.NOT_FOUND,ex.getMessage());
    }
}