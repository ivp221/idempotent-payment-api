package com.IshaVishakhaPramod.IdempotentPaymentApi.GlobalExceptionHandler;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.ErrorDetails;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Exception.InvalidIdempotencyKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdempotencyKeyException.class)
    public ResponseEntity<ErrorDetails> handleIdempotencyKeyNotFound(InvalidIdempotencyKeyException ex){
        ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST,ex.getMessage());
        return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDetails> handleMissingRequestHederExceptiona(MissingRequestHeaderException ex){
        ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST, "Required Header : "+ ex.getHeaderName() +" is missing");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
