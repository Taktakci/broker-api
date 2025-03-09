package com.taktakci.brokerapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BrokerException.class)
    public ResponseEntity<GenericErrorDto> brokerException(BrokerException exception) {
        log.error("BrokerException occured - {}", exception.getDescription());
        GenericErrorDto errorDto = new GenericErrorDto();
        errorDto.setError(exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorDto> argumentException(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException occured - {}", exception.getMessage());
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );
        GenericErrorDto errorDto = new GenericErrorDto();
        errorDto.setError(errors.values().toString());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
