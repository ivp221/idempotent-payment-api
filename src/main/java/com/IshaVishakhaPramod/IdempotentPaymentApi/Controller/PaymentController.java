package com.IshaVishakhaPramod.IdempotentPaymentApi.Controller;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.MakePaymentRequest;
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
    public ResponseEntity<PaymentResponse> makePayment(@RequestBody MakePaymentRequest req){
        PaymentResponse response = paymentService.savePayment(req.getCustomerId(), req.getAmount(), req.getCurrency());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
