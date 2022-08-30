package com.splb.model.dao.implementation;

import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentDAOImplTest {

    private final static EnrollmentDAO dao = EnrollmentDAOImpl.getInstance();
    private final static FacultyDAO fdao = FacultyDAOImpl.getInstance();


    @Test
    void add() throws EnrollmentDAOException, FacultyDAOException {

        Assert.assertEquals (fdao.getFacultyById(1).getTotalPlaces(), 10);
        Assert.assertEquals (fdao.getFacultyById(1).getBudgetPlaces(), 3);
        Assert.assertEquals (fdao.getFacultyById(2).getTotalPlaces(), 10);
        Assert.assertEquals (fdao.getFacultyById(2).getBudgetPlaces(), 2);
        Assert.assertEquals (fdao.getFacultyById(3).getTotalPlaces(), 10);
        Assert.assertEquals (fdao.getFacultyById(3).getBudgetPlaces(), 3);
        Assert.assertTrue(dao.add(1, 1, 0));
        Assert.assertTrue(dao.add(1, 2, 1));
        Assert.assertTrue(dao.add(1, 3, 2));
        Assert.assertTrue(dao.add(2, 4, 0));
        Assert.assertTrue(dao.add(2, 5, 1));
        Assert.assertTrue(dao.add(2, 6, 2));

    }
}