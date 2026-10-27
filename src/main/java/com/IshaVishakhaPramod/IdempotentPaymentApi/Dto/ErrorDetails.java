package com.IshaVishakhaPramod.IdempotentPaymentApi.Dto;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorDetails {
    HttpStatus status;
    String message;

}
