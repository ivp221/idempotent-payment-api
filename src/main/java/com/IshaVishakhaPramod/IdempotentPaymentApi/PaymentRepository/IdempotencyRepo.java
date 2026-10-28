package com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Idempotency;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface IdempotencyRepo extends JpaRepository<Idempotency, Long> {
    Optional<Idempotency> findByIdempotencyKey(String idempotencyKey);

    @Transactional
    @Modifying
    @Query(
"""
DELETE FROM Idempotency i WHERE i.expiresAt < :now AND i.status= :completed
""")
    int deleteExpiredIdempotencyKeys(@Param("now")Instant now, @Param("completed")TransactionStatus completed);
}
