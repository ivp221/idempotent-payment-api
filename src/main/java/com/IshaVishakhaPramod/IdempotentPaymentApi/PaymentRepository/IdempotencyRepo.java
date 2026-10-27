package com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Idempotency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyRepo extends JpaRepository<Idempotency, Long> {
    Optional<Idempotency> findByIdempotencyKey(String idempotencyKey);
}
