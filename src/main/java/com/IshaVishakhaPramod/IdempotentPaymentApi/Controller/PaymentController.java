package com.IshaVishakhaPramod.IdempotentPaymentApi.Controller;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.MakePaymentRequest;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Exception.InvalidIdempotencyKeyException;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody MakePaymentRequest req , @RequestHeader(value = "Idempotency-Key") String idempotencyKey){
        if (idempotencyKey.isEmpty()|| idempotencyKey.isBlank()){
            throw new InvalidIdempotencyKeyException("Idempotency key passed is : "+idempotencyKey);
        }else if (idempotencyKey.length() > 255){
            throw new InvalidIdempotencyKeyException("Idempotency Key has exceeded the maximum character limit.");
        }else if (idempotencyKey.strip().length() != idempotencyKey.length()){
            throw new InvalidIdempotencyKeyException("Idempotency Key has trailing or starting white spaces which is not allowed.");
        }
        PaymentResponse response = paymentService.savePayment(req,idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
