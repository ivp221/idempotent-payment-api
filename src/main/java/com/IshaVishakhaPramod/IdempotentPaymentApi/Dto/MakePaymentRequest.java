package com.IshaVishakhaPramod.IdempotentPaymentApi.Dto;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
public class MakePaymentRequest {
    private long customerId;
    private BigDecimal amount;
    private String currency;

}
