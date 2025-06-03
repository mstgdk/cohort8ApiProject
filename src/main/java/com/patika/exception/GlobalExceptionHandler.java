package com.patika.exception;

import com.patika.exception.message.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object>handleConflictException(ConflictException ex,
                                                         WebRequest request){
        ApiResponseError error = new ApiResponseError(HttpStatus.CONFLICT, ex.getMessage(), request.getDescription(false));

        return  new ResponseEntity<>(error,error.getStatus());

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>handleGeneralException(ConflictException ex,
                                                         WebRequest request){
        ApiResponseError error = new ApiResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen bir hata oluştu", request.getDescription(false));

        return  new ResponseEntity<>(error,error.getStatus());

    }
}
