package com.future.cinemaxx.exceptionHandling;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ErrorMessage createErrorMessage(HttpStatus httpStatus, Exception exception, WebRequest request){
        return new ErrorMessage(
                httpStatus.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        var httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException exception, WebRequest webRequest){
        var httpStatus = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception exception, WebRequest webRequest){
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> illegalStateExceptionHandler(Exception exception, WebRequest webRequest){
        var httpStatus = HttpStatus.CONFLICT;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(Exception exception, WebRequest webRequest){
        var httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }
}
