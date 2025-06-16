package com.nurman.webtoon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.nurman.webtoon.model.WebResponse;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler
    public ResponseEntity<WebResponse<String>> constraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder().error(e.getMessage()).build());
    }

    @ExceptionHandler
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(WebResponse.<String>builder().error(e.getReason()).build());
    }
}
