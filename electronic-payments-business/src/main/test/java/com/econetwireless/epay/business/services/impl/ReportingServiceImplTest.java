package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.dao.subscriberrequest.api.SubscriberRequestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportingServiceImplTest {
    @Mock
    SubscriberRequestDao subscriberRequestDao;

    @InjectMocks
    ReportingServiceImpl reportingService;

    @Test
    public void findRequestsByPartnerCode_fetchesRequests() {

        final String partnerCode = "ABC";
        reportingService.findSubscriberRequestsByPartnerCode(partnerCode);

        verify(subscriberRequestDao, times(1)).findByPartnerCode(eq(partnerCode));
    }
}