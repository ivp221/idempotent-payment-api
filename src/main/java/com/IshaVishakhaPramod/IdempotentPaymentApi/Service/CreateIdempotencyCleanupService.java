package com.IshaVishakhaPramod.IdempotentPaymentApi.Service;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.TransactionStatus;
import com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository.IdempotencyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;

@RequiredArgsConstructor
public class CreateIdempotencyCleanupService {
    private final IdempotencyRepo idempotencyRepo;

    @Scheduled(fixedDelayString = "300000")
    public void cleanupExpiredKeys(){
        int deleted = idempotencyRepo.deleteExpiredIdempotencyKeys(Instant.now(), TransactionStatus.COMPLETED);
        System.out.println("Deleted Expired idempotency keys" + deleted);
    }

}
