package com.patika.exception.message;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiResponseError {
    private HttpStatus status;

    private LocalDateTime timeStamp;

    private String message;

    private String requestURL;

    private ApiResponseError() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponseError(HttpStatus status, String message, String requestURL) {
        this();
        this.status = status;
        this.message = message;
        this.requestURL = requestURL;
    }
}
