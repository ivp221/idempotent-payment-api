package com.IshaVishakhaPramod.IdempotentPaymentApi.Entity;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "idempotency")
@NoArgsConstructor
@Getter
@Setter
public class Idempotency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Column(name = "request_hash", nullable = false, length = 64)
    private String requestHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "response_body", columnDefinition = "TEXT")
    private String responseBody;

    @Column(name = "created_at", nullable = false,updatable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private Instant expiresAt;

    public Idempotency(String idempotencyKey, String requestHash){
        this.idempotencyKey = idempotencyKey;
        this.requestHash = requestHash;
        this.status = TransactionStatus.IN_PROGRESS;
        this.createdAt = Instant.now();
    }

}
