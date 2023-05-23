package com.example.eventlybackend.evently.exception;


import com.example.eventlybackend.evently.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * The GlobalExceptionHandler class is a Spring Boot exception handler that handles global exceptions thrown within
 * the application. It provides specific exception handling for the ResourceNotFoundException and
 * MethodArgumentNotValidException.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle the ResourceNotFoundException and return a ResponseEntity with an appropriate error response.
     *
     * @param ex The ResourceNotFoundException that was thrown.
     * @return A ResponseEntity with an ApiResponse indicating the error response.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle the MethodArgumentNotValidException and return a ResponseEntity with an appropriate error response.
     *
     * @param ex The MethodArgumentNotValidException that was thrown.
     * @return A ResponseEntity with a Map containing field names and error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}

