package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.entity.ApplicantResult;
import com.splb.service.exceptions.ApplicantResultServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

class ApplicantResultServiceTest {

    static ApplicantResultService srv;
    final static ApplicantResult RESULT = new ApplicantResult(14, 1, 12, 1, 1, 5, 1);

    @BeforeEach
    void setUp() {
        srv = new ApplicantResultService(new DirectConnectionBuilder());
    }

    @Test
    void insert() throws ApplicantResultServiceException {

        Assert.assertTrue(srv.insert(RESULT));
        Assert.assertThrows(ApplicantResultServiceException.class,
                () -> srv.insert(RESULT));
    }

    @Test
    void addFResult() throws ApplicantResultServiceException {
        boolean result = srv.addFResult(14, 12, 12, 1);
        Assert.assertTrue(result);
        Assert.assertThrows(ApplicantResultServiceException.class,
                () -> srv.addFResult(14, 12, 12, 1));
    }

    @Test
    void get() throws ApplicantResultServiceException {
        ApplicantResult result = srv.get(14);
        Assert.assertEquals(result.getAlgebra(), RESULT.getAlgebra());
        Assert.assertEquals(result.getBiology(), RESULT.getBiology());
        Assert.assertEquals(result.getChemistry(), RESULT.getChemistry());
        Assert.assertEquals(result.getEnglish(), RESULT.getEnglish());
        Assert.assertEquals(result.getLiterature(), RESULT.getLiterature());
        Assert.assertEquals(result.getWorldHistory(), RESULT.getWorldHistory());
    }

    @Test
    void checkSub() throws ApplicantResultServiceException {
        boolean sub1 = srv.checkSub(1);
        boolean sub2 = srv.checkSub(2);
        boolean sub3 = srv.checkSub(3);
        boolean sub4 = srv.checkSub(4);
        Assert.assertFalse(sub1);
        Assert.assertTrue(sub2);
        Assert.assertTrue(sub3);
        Assert.assertTrue(sub4);
    }
}