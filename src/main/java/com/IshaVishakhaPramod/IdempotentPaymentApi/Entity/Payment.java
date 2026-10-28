package com.IshaVishakhaPramod.IdempotentPaymentApi.Entity;

/*
* Learning :
* @Column(nullable = false) describes the db mapping but does not replace the request validation. Validation has to still be seperately implemented.

*
*  */

import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, length =3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false, updatable = false)

    private Instant createdAt;



    @PrePersist
    public void onCreate(){
        if (createdAt == null){
            createdAt = Instant.now();
        }
    }

    public Payment(Long customerId, BigDecimal amount, String currency) {
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.status = Status.PENDING;
    }
    public void markSuccessful(){
        this.status = Status.SUCCESS;
    }
    public void markFailed(){
        this.status = Status.FAILED;

    }
}
