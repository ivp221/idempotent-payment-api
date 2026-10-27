package com.IshaVishakhaPramod.IdempotentPaymentApi.Exception;

public class InvalidIdempotencyKeyException extends RuntimeException{
    public InvalidIdempotencyKeyException(String message){
        super(message);
    }

}
