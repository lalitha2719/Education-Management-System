package com.ems.exception;

import com.ems.entity.ExceptionResponse;
import feign.RetryableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<ExceptionResponse> invalidTokenException(final InvalidTokenException invalidTokenException) {
        return (ResponseEntity<ExceptionResponse>) new ResponseEntity((Object) new ExceptionResponse(invalidTokenException.getMessage(), LocalDateTime.now(), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({FacultyNotFoundException.class})
    public ResponseEntity<ExceptionResponse> facultyNotFoundException(final FacultyNotFoundException facultyNotFoundException) {
        return (ResponseEntity<ExceptionResponse>) new ResponseEntity((Object) new ExceptionResponse(facultyNotFoundException.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CourseNotFoundException.class})
    public ResponseEntity<ExceptionResponse> courseNotFoundException(final CourseNotFoundException courseNotFoundException) {
        return (ResponseEntity<ExceptionResponse>) new ResponseEntity((Object) new ExceptionResponse(courseNotFoundException.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RetryableException.class})
    public ResponseEntity<ExceptionResponse> microServiceUnavailableException() {
        return (ResponseEntity<ExceptionResponse>) new ResponseEntity((Object) new ExceptionResponse("MicroServiceUnavailable", LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}