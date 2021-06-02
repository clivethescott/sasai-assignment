package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.pojo.INCreditResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditsServiceImplTest {

    private final ArgumentCaptor<SubscriberRequest> subscriberRequestArgumentCaptor = ArgumentCaptor.forClass(SubscriberRequest.class);

    @Mock
    ChargingPlatform chargingPlatform;

    @Mock
    SubscriberRequestDao subscriberRequestDao;

    @InjectMocks
    CreditsServiceImpl creditsService;

    private INCreditResponse successCreditResponse() {
        final INCreditResponse creditResponse = new INCreditResponse();
        creditResponse.setResponseCode("200");
        return creditResponse;
    }

    @Test
    public void creditAirtime_savesRequest() {
        final String msisdn = "772355855";
        final double amount = 3.55D;
        when(chargingPlatform.creditSubscriberAccount(any())).thenReturn(successCreditResponse());
        final SubscriberRequest expectedSubscriberRequest = new SubscriberRequest();
        expectedSubscriberRequest.setAmount(amount);
        expectedSubscriberRequest.setMsisdn(msisdn);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(expectedSubscriberRequest);
        final AirtimeTopupRequest topupRequest = createTopupRequest(msisdn, amount);
        creditsService.credit(topupRequest);

        verify(subscriberRequestDao, atLeastOnce()).save(subscriberRequestArgumentCaptor.capture());

        final SubscriberRequest actualSubscriberRequest = subscriberRequestArgumentCaptor.getValue();

        assertEquals(msisdn, actualSubscriberRequest.getMsisdn());
        final double delta = 0.0001D;
        assertEquals(amount, actualSubscriberRequest.getAmount(), delta);
    }

    private AirtimeTopupRequest createTopupRequest(String msisdn, double amount) {
        final AirtimeTopupRequest topupRequest = new AirtimeTopupRequest();
        topupRequest.setMsisdn(msisdn);
        topupRequest.setAmount(amount);
        return topupRequest;
    }
}