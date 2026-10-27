package com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {


}
