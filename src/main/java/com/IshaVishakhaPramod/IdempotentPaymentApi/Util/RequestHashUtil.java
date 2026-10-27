package com.IshaVishakhaPramod.IdempotentPaymentApi.Util;

import com.IshaVishakhaPramod.IdempotentPaymentApi.Dto.MakePaymentRequest;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;

public final class RequestHashUtil {
    private RequestHashUtil(){
        //Utility class to prevent instantiation
    }

    public static String canonicalizeMakePaymentRequest(MakePaymentRequest req){
        Long customerId = req.getCustomerId();
        BigDecimal normalizedAmount = req.getAmount().stripTrailingZeros();
        String amount = normalizedAmount.toPlainString();
        String currency = req.getCurrency().trim().toUpperCase(Locale.ROOT);
        return "customerId="+ customerId+"\namount="+amount+"\ncurrency="+currency;
    }
    public static String sha256(String canonicalizedRequestBody){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(canonicalizedRequestBody.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 is unavailable ", e);
        }
    }
}
