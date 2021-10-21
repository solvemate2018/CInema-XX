package com.future.cinemaxx.exceptionHandling;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("path",((ServletWebRequest)request).getRequest().getRequestURI().toString());
        errorBody.put("status","400");
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err-> err.getField()+": "+err.getDefaultMessage())
                .collect(Collectors.toList());

        errorBody.put("error", String.join(",",errors));
        return new ResponseEntity<Object>(errorBody, HttpStatus.BAD_REQUEST);
    }

    private ErrorMessage createErrorMessage(HttpStatus httpStatus, Exception exception, WebRequest request) {
        return new ErrorMessage(
                httpStatus.value(),
                new Date(),
                exception.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        var httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        var httpStatus = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> illegalStateExceptionHandler(Exception exception, WebRequest webRequest) {
        var httpStatus = HttpStatus.CONFLICT;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(Exception exception, WebRequest webRequest) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createErrorMessage(httpStatus, exception, webRequest), httpStatus);
    }

    @ExceptionHandler(Client4xxException.class)
    public ResponseEntity<Map<String, String>> handleException(HttpServletRequest request, Client4xxException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("path",request.getRequestURI().toString());
        errorResponse.put("error", e.getLocalizedMessage());
        errorResponse.put("status", ""+HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
