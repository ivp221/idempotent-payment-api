package com.IshaVishakhaPramod.IdempotentPaymentApi.Dto;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Payment;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long customerId;
    private BigDecimal amount;
    private String currency;
    private Status status;
    private Instant createdAt;

    public static PaymentResponse toPaymentResponse(Payment p){
        return new PaymentResponse(p.getId(), p.getCustomerId(), p.getAmount(), p.getCurrency(), p.getStatus(), p.getCreatedAt());

    }
}

