package com.IshaVishakhaPramod.IdempotentPaymentApi.Service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FakePaymentProvider {
    public boolean charge(BigDecimal amount){
        return true;
    }
}
