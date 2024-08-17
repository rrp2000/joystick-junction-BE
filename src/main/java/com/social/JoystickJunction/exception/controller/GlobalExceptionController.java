package com.social.JoystickJunction.exception.controller;


import com.social.JoystickJunction.exception.ErrorDetails;
import com.social.JoystickJunction.exception.PostServiceException;
import com.social.JoystickJunction.exception.UserServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorDetails> handleUserException(UserServiceException userException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(userException.getMessage());
        HttpStatus status;
        if (userException.getHttpStatus() == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            status = userException.getHttpStatus();
        }
        errorDetails.setStatus(status);
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(PostServiceException.class)
    public ResponseEntity<ErrorDetails> handlePostServiceException(PostServiceException postException) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(postException.getMessage());
        HttpStatus status;
        if (postException.getHttpStatus() == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            status = postException.getHttpStatus();
        }
        errorDetails.setStatus(status);
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
