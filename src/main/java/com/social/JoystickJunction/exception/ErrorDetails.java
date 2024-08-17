package com.social.JoystickJunction.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private String message;
    private HttpStatus status;
    private LocalDateTime timeStamp;
}
