package com.trevisan.CalculadorMIcroServices.infra;

import com.trevisan.CalculadorMIcroServices.Exceptions.IndexOutOfBoundsListValuesException;
import com.trevisan.CalculadorMIcroServices.Exceptions.InvalidInputFormatException;
import com.trevisan.CalculadorMIcroServices.Exceptions.ListValuesEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>genericException(Exception ex){
        return new ResponseEntity<>(
                ApiError
                    .builder()
                        .timestamp(LocalDateTime.now())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(List.of(ex.getMessage()))
                    .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(ListValuesEmptyException.class)
    public ResponseEntity<ApiError>listValueEmptyException(Exception ex){
        return new ResponseEntity<>(
                ApiError
                    .builder()
                        .timestamp(LocalDateTime.now())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(List.of(ex.getMessage()))
                    .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(IndexOutOfBoundsListValuesException.class)
    public ResponseEntity<ApiError>indexOutOfBoundsListValuesException(Exception ex){
        return new ResponseEntity<>(
                ApiError
                    .builder()
                        .timestamp(LocalDateTime.now())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(List.of(ex.getMessage()))
                    .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(InvalidInputFormatException.class)
    public ResponseEntity<ApiError>invalidInputException(Exception ex){
        return new ResponseEntity<>(
                ApiError
                    .builder()
                        .timestamp(LocalDateTime.now())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorStatus(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .message(List.of(ex.getMessage()))
                    .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
