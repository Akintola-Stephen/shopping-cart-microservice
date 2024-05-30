package com.microservice.orderservice.exception;

import com.microservice.orderservice.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(OrderServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(OrderServiceCustomException ex) {
        new ErrorResponse();
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .build(),
                HttpStatus.valueOf(ex.getStatus()));

    }
}
