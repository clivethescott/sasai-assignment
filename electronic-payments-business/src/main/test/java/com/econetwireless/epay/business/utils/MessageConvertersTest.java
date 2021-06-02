package com.econetwireless.epay.business.utils;

import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageConvertersTest {

    @Test
    public void nullCreditRequest_GivesNullResponse() {
        final INCreditRequest inCreditRequest = null;
       assertNull(MessageConverters.convert(inCreditRequest));
    }

    @Test
    public void correctConversionOfCreditRequests() {
        final INCreditRequest creditRequest = new INCreditRequest();
        final String msisdn = "771";
        final double amount = 22.5D;
        final String partnerCode = "ABC";
        final String referenceNumber = "111-222";

        creditRequest.setAmount(amount);
        creditRequest.setMsisdn(msisdn);
        creditRequest.setPartnerCode(partnerCode);
        creditRequest.setReferenceNumber(referenceNumber);

        final CreditRequest convertedRequest = MessageConverters.convert(creditRequest);

        assertEquals(msisdn, convertedRequest.getMsisdn());
        final double delta = 0.00001D;
        assertEquals(amount, convertedRequest.getAmount(), delta);
        assertEquals(partnerCode, convertedRequest.getPartnerCode());
        assertEquals(referenceNumber, convertedRequest.getReferenceNumber());
    }

    @Test
    public void nullCreditResponse_GivesNullResponse() {
        final INCreditResponse response = null;
        assertNull(MessageConverters.convert(response));
    }

    @Test
    public void correctConversionOfCreditResponses() {

        final String msisdn = "771";
        final double balance = 22.5D;
        final String narrative = "ABC";
        final String responseCode = "500";

        final CreditResponse creditResponse = new CreditResponse();
        creditResponse.setBalance(balance);
        creditResponse.setMsisdn(msisdn);
        creditResponse.setNarrative(narrative);
        creditResponse.setResponseCode(responseCode);
        final INCreditResponse convertedResponse = MessageConverters.convert(creditResponse);

        assertEquals(msisdn, convertedResponse.getMsisdn());
        final double delta = 0.00001D;
        assertEquals(balance, convertedResponse.getBalance(), delta);
        assertEquals(narrative, convertedResponse.getNarrative());
        assertEquals(responseCode, convertedResponse.getResponseCode());
    }

    @Test
    public void nullBalance_GivesNullResponse() {
        final INCreditResponse response = null;
        assertNull(MessageConverters.convert(response));
    }

    @Test
    public void correctBalanceResponseConversion() {

        final String msisdn = "771";
        final double amount = 33.3D;
        final String narrative = "ABC";
        final String responseCode = "500";

        final BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setMsisdn(msisdn);
        balanceResponse.setResponseCode(responseCode);
        balanceResponse.setNarrative(narrative);
        balanceResponse.setAmount(amount);

        final INBalanceResponse convertedResponse = MessageConverters.convert(balanceResponse);
        assertEquals(msisdn, convertedResponse.getMsisdn());
        final double delta = 0.0001;
        assertEquals(amount, convertedResponse.getAmount(), delta);
        assertEquals(narrative, convertedResponse.getNarrative());
        assertEquals(responseCode, convertedResponse.getResponseCode());
    }
}