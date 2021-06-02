package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.integrations.api.ChargingPlatform;
import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.pojo.INBalanceResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnquiriesServiceImplTest {

    @Mock
    ChargingPlatform chargingPlatform;

    @Mock
    SubscriberRequestDao subscriberRequestDao;

    @InjectMocks
    EnquiriesServiceImpl enquiriesService;

    @Test
    public void enquire_saveRequestAndEnquiresBalance() {

        final INBalanceResponse successBalanceResponse = new INBalanceResponse();
        successBalanceResponse.setResponseCode("200");
        when(chargingPlatform.enquireBalance(anyString(), anyString())).thenReturn(successBalanceResponse);
        when(subscriberRequestDao.save(any(SubscriberRequest.class))).thenReturn(new SubscriberRequest());
        final String partnerCode = "ABC";
        final String msisdn = "771222674";
        enquiriesService.enquire(partnerCode, msisdn);

        verify(subscriberRequestDao, atLeastOnce()).save(any(SubscriberRequest.class));
        verify(chargingPlatform, times(1)).enquireBalance(partnerCode, msisdn);
    }
}