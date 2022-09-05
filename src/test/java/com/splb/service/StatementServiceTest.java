package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.model.entity.StatementResult;
import com.splb.service.exceptions.StatementServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementServiceTest {

    static StatementService srv;

    @BeforeEach
    void setUp() {
        srv = new StatementService(new DirectConnectionBuilder());
    }

    @Test
    void addAntRemoveTest() throws StatementServiceException {
        boolean add11 = srv.add(1, 1);
        boolean add12 = srv.add(1, 2);
        boolean add13 = srv.add(1, 3);
        Assert.assertTrue(add11);
        Assert.assertTrue(add12);
        Assert.assertTrue(add13);
        Assert.assertThrows(StatementServiceException.class,
                () -> srv.add(1, 1));
        Assert.assertThrows(StatementServiceException.class,
                () -> srv.add(1, 2));
        Assert.assertThrows(StatementServiceException.class,
                () -> srv.add(1, 3));
        boolean remove11 = srv.remove(1, 1);
        boolean remove12 = srv.remove(1, 2);
        boolean remove13 = srv.remove(1, 3);
        Assert.assertTrue(remove11);
        Assert.assertTrue(remove12);
        boolean removeFalse11 = srv.remove(1, 1);
        boolean removeFalse12 = srv.remove(1, 2);
        Assert.assertFalse(removeFalse11);
        Assert.assertFalse(removeFalse12);
    }


    @Test
    void checkTest() throws StatementServiceException {
        boolean check11 = srv.check(1, 1);
        boolean check12 = srv.check(1, 2);
        Assert.assertFalse(check11);
        Assert.assertFalse(check12);
    }

    @Test
    void getListTest() throws StatementServiceException {
        List<StatementResult> list1 = srv.get(2);
        List<StatementResult> list2 = srv.get(3);
        List<StatementResult> list3 = srv.get(5);
        List<Statement> statementList = srv.getList();
        List<Faculty> applicantsList1 = srv.getApplicantsList(2);
        List<Faculty> applicantsList2 = srv.getApplicantsList(3);
        List<Faculty> applicantsList3 = srv.getApplicantsList(5);
        Assert.assertEquals(list1.size(), 2);
        Assert.assertEquals(list2.size(), 2);
        Assert.assertEquals(list3.size(), 5);
        Assert.assertEquals(statementList.size(), 0);
        Assert.assertEquals(applicantsList1.size(), 2);
        Assert.assertEquals(applicantsList2.size(), 2);
        Assert.assertEquals(applicantsList3.size(), 5);

    }

}