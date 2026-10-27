package com.IshaVishakhaPramod.IdempotentPaymentApi.Service;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Payment;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.Status;
import com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


import static com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse.toPaymentResponse;


/*
* Learning outcomes :

* save() does not imply atomicity with an external provider.
 */

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {
    private final PaymentRepo paymentRepo;
    private final FakePaymentProvider fakePaymentProvider;

    public PaymentResponse savePayment(Long customerId, BigDecimal amount, String currency){
        Payment payment = new Payment(customerId,amount,currency);
        Payment savedPayment = paymentRepo.save(payment);
        boolean charged = fakePaymentProvider.charge(amount);
        if (charged){
            return updateToSuccess(savedPayment.getId());
        }else {
            return updateToFailed(savedPayment.getId());
        }

    }


    private PaymentResponse updateStatus(Long id,Status status){
        Payment p = paymentRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Payment with id " + id + " not found"));
        if (status == Status.SUCCESS){
            p.markSuccessful();
        }else if (status == Status.FAILED){
            p.markFailed();
        }else{
            throw new IllegalArgumentException("Illegal Argument Status (Enter SUCCESS or FAILED) . You entered : " + status );
        }



        Payment updatedPayment = paymentRepo.save(p);
        return toPaymentResponse(updatedPayment);

    }
    public PaymentResponse updateToSuccess(Long id){
        return updateStatus(id, Status.SUCCESS);
    }
    public PaymentResponse updateToFailed(Long id){
        return updateStatus(id, Status.FAILED);
    }





}
