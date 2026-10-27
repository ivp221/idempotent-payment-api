package com.IshaVishakhaPramod.IdempotentPaymentApi.Util;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.MakePaymentRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RequestHashUtilTest {
    private MakePaymentRequest createRequest(Long customerId, String amount, String currency){
        MakePaymentRequest request = new MakePaymentRequest();
        request.setCustomerId(customerId);
        request.setAmount(new BigDecimal(amount));
        request.setCurrency(currency);
        return request;
    }

    @Test
    public void currencyCaseShouldNotChangeCanonicalString() {
        MakePaymentRequest request1 =
                createRequest(101L, "499.99", "inr");
        MakePaymentRequest request2 = createRequest(101L, "499.99", "inr ");
        assertEquals(RequestHashUtil.canonicalizeMakePaymentRequest(request1),RequestHashUtil.canonicalizeMakePaymentRequest(request2));

    }

    @Test
    public void diffCustomersdiffCanonicalString(){
        MakePaymentRequest request1 =
                createRequest(100L, "499.99", "inr");
        MakePaymentRequest request2 = createRequest(101L, "499.99", "inr");
        assertNotEquals(RequestHashUtil.canonicalizeMakePaymentRequest(request1),RequestHashUtil.canonicalizeMakePaymentRequest(request2));

    }
    @Test
    public void equivalentAmountSameCanonicalString() {
        MakePaymentRequest request1 =
                createRequest(101L, "499.990", "inr");
        MakePaymentRequest request2 = createRequest(101L, "499.99", "inr");
        assertEquals(RequestHashUtil.canonicalizeMakePaymentRequest(request1),RequestHashUtil.canonicalizeMakePaymentRequest(request2));

    }

    @Test
    public void equivalentRequestsSameanonicalString() {
        MakePaymentRequest request1 =
                createRequest(101L, "499.99", "inr");
        MakePaymentRequest request2 = createRequest(101L, "499.99", "inr");
        assertEquals(RequestHashUtil.canonicalizeMakePaymentRequest(request1),RequestHashUtil.canonicalizeMakePaymentRequest(request2));

    }



    }
