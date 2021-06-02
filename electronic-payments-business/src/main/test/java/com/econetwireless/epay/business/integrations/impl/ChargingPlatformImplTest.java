package com.econetwireless.epay.business.integrations.impl;

import com.econetwireless.epay.business.utils.MessageConverters;
import com.econetwireless.in.webservice.BalanceResponse;
import com.econetwireless.in.webservice.CreditRequest;
import com.econetwireless.in.webservice.CreditResponse;
import com.econetwireless.in.webservice.IntelligentNetworkService;
import com.econetwireless.utils.pojo.INBalanceResponse;
import com.econetwireless.utils.pojo.INCreditRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChargingPlatformImplTest {

    final ArgumentCaptor<CreditRequest> creditRequestArgumentCaptor = ArgumentCaptor.forClass(CreditRequest.class);
    @Mock
    IntelligentNetworkService intelligentNetworkService;

    @InjectMocks
    ChargingPlatformImpl chargingPlatform;

    private BalanceResponse createBalance(double amount) {
        final BalanceResponse response = new BalanceResponse();
        response.setAmount(amount);
        return response;
    }

    private CreditRequest createCreditRequest(double amount, String msisdn, String partnerCode, String ref) {
        final CreditRequest response = new CreditRequest();
        response.setAmount(amount);
        response.setMsisdn(msisdn);
        response.setPartnerCode(partnerCode);
        response.setReferenceNumber(ref);
        return response;
    }

    private CreditResponse createCredit(double balance, String msisdn, String narrative, String responseCode) {
        final CreditResponse response = new CreditResponse();
        response.setBalance(balance);
        response.setMsisdn(msisdn);
        response.setNarrative(narrative);
        response.setResponseCode(responseCode);
        return response;
    }

    private BalanceResponse createBalance(double amount, String msisdn, String narrative, String responseCode) {
        final BalanceResponse response = new BalanceResponse();
        response.setAmount(amount);
        response.setMsisdn(msisdn);
        response.setNarrative(narrative);
        response.setResponseCode(responseCode);
        return response;
    }

    @Test
    public void givenExistingBalance_weInquireCorrectBalance() {

        final double expectedBalance = 44.0D;
        when(intelligentNetworkService.enquireBalance(anyString(), anyString()))
                .thenReturn(createBalance(expectedBalance));

        final String msisdn = "772788224";
        final String partnerCode = "AIRTIME";

        final double actualBalance = chargingPlatform.enquireBalance(partnerCode, msisdn).getAmount();

        final double delta = 0.0001;
        Assert.assertEquals(expectedBalance, actualBalance, delta);

        verify(intelligentNetworkService, times(1)).enquireBalance(eq(partnerCode), eq(msisdn));
    }

    @Test
    public void givenExistingBalance_weGetCorrectInquiryResponse() {

        final BalanceResponse balance = createBalance(22.5D, "XYZ", "Airtime request failed", "500");

        when(intelligentNetworkService.enquireBalance(anyString(), anyString()))
                .thenReturn(balance);

        final INBalanceResponse expectedBalanceResponse = MessageConverters.convert(balance);
        final INBalanceResponse actualBalanceResponse = chargingPlatform.enquireBalance("AIRTIME", "XYZ");

        Assert.assertEquals(expectedBalanceResponse, actualBalanceResponse);

    }

    @Test
    public void givenExistingSubscriber_weCanCreditAccount() {

        final CreditResponse creditResponse = createCredit(13.4D, "ABC", "", "200");
        when(intelligentNetworkService.creditSubscriberAccount(any(CreditRequest.class)))
                .thenReturn(creditResponse);

        final CreditRequest expectedCreditRequest = createCreditRequest(13.4D, "ABC", "", "200");

        final INCreditRequest inCreditRequest = new INCreditRequest();
        inCreditRequest.setAmount(13.4D);
        inCreditRequest.setMsisdn("ABC");
        chargingPlatform.creditSubscriberAccount(inCreditRequest);
        verify(intelligentNetworkService, times(1)).creditSubscriberAccount(creditRequestArgumentCaptor.capture());

        final CreditRequest actualCreditRequest = creditRequestArgumentCaptor.getValue();

        final double delta = 0.0001;
        Assert.assertEquals(expectedCreditRequest.getAmount(), actualCreditRequest.getAmount(), delta);
        Assert.assertEquals(expectedCreditRequest.getMsisdn(), actualCreditRequest.getMsisdn());

    }
}