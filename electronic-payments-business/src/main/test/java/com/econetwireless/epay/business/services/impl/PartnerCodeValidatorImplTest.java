package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.epay.domain.RequestPartner;
import com.econetwireless.utils.execeptions.EpayException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PartnerCodeValidatorImplTest {

    @Mock
    RequestPartnerDao requestPartnerDao;

    @InjectMocks
    PartnerCodeValidatorImpl partnerCodeValidator;

    @Test(expected = EpayException.class)
    public void givenNoPartnerFound_partnerIsNotValid() {
        when(requestPartnerDao.findByCode(any())).thenReturn(null);
        partnerCodeValidator.validatePartnerCode("XYZ");
    }

    @Test
    public void givenPartnerFound_partnerIsValid() {
        when(requestPartnerDao.findByCode(any())).thenReturn(new RequestPartner());
        assertTrue(partnerCodeValidator.validatePartnerCode("XYZ"));
    }
}