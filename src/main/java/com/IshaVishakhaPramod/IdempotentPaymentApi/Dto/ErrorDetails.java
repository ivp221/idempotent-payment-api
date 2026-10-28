package com.IshaVishakhaPramod.IdempotentPaymentApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus status;
    private String message;

}
