package com.IshaVishakhaPramod.IdempotentPaymentApi.Service;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.MakePaymentRequest;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.PaymentResponse;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Idempotency;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Entity.Payment;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.Status;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Enum.TransactionStatus;
import com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository.IdempotencyRepo;
import com.IshaVishakhaPramod.IdempotentPaymentApi.PaymentRepository.PaymentRepo;
import com.IshaVishakhaPramod.IdempotentPaymentApi.Util.RequestHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;


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
    private final IdempotencyRepo idempotencyRepo;
    private final ObjectMapper objectMapper;


    public boolean claimPayment(MakePaymentRequest req, String idempotencyKey){
        System.out.println("Claim Payment Is Called");


        String hashNew = RequestHashUtil.sha256(RequestHashUtil.canonicalizeMakePaymentRequest(req));
        Optional<Idempotency> existing = idempotencyRepo.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()){
            if (existing.get().getRequestHash().equals(hashNew)){
                return false;
            }else{
                throw new IllegalArgumentException("Different request with same idempotency key");
            }
        }else {
            Idempotency idempotency = new Idempotency(idempotencyKey, hashNew);
            try {
                idempotencyRepo.saveAndFlush(idempotency);
            } catch (DataIntegrityViolationException e) {
                System.out.println("DUPLICATE CLAIM: " + idempotencyKey);
                Optional<Idempotency> result = idempotencyRepo.findByIdempotencyKey(idempotencyKey);
                if (result.isEmpty()){
                    System.out.println("No existing record found after insert failure");
                }
                Idempotency existingRecord = result.get();
                if (existingRecord.getRequestHash().equals(hashNew)) {
                    return false;
                }
                throw new IllegalArgumentException("Different Request with same idempotency key");
            }
            return true;
        }


    }
    @Transactional
    public PaymentResponse savePayment(MakePaymentRequest req, String idempotencyKey){
        if (!claimPayment(req,idempotencyKey)){
            Idempotency idem = idempotencyRepo.findByIdempotencyKey(idempotencyKey).get();
            if (idem.getStatus().equals(TransactionStatus.IN_PROGRESS)){
                throw new IllegalStateException("Payment Processing");
            }
            String existing = idem.getResponseBody();
            try {
                return objectMapper.readValue(existing, PaymentResponse.class);
            } catch (JacksonException e) {
                throw new RuntimeException(e);
            }
        }
        Payment payment = new Payment(req.getCustomerId(),req.getAmount(),req.getCurrency());
        Idempotency idem = idempotencyRepo.findByIdempotencyKey(idempotencyKey).get();

        Payment savedPayment = paymentRepo.save(payment);

        boolean charged = fakePaymentProvider.charge(req.getAmount());
        if (charged){

            PaymentResponse response = updateToSuccess(savedPayment.getId());
            idem.setResponseBody(objectMapper.writeValueAsString(response));
            idem.setStatus(TransactionStatus.COMPLETED);
            idempotencyRepo.saveAndFlush(idem);
            return response;
        }else {
            PaymentResponse response = updateToFailed(savedPayment.getId());
            idem.setResponseBody(objectMapper.writeValueAsString(response));
            idem.setStatus(TransactionStatus.COMPLETED);
            idempotencyRepo.saveAndFlush(idem);
            return response;
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
