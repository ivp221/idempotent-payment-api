package com.IshaVishakhaPramod.IdempotentPaymentApi.GlobalExceptionHandler;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.ErrorDetails;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Exception.InvalidIdempotencyKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex){

        Map<String, String> body = new HashMap<>();
        body.put("error", ex.getMessage());
        ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.badRequest().body(error) ;
    }

}
