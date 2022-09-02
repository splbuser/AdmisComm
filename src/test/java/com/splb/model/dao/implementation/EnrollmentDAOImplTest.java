package com.splb.model.dao.implementation;

import com.splb.model.dao.EnrollmentDAO;
import com.splb.model.dao.FacultyDAO;
import com.splb.model.dao.StatementDAO;
import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.EnrollmentDAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Enrollment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentDAOImplTest {
    private final static EnrollmentDAO dao = EnrollmentDAOImpl.getInstance();
    private final static FacultyDAO fdao = FacultyDAOImpl.getInstance();
    static Connection con;

    @BeforeAll
    static void setUp() throws Exception {
        con = new DirectConnectionBuilder().getConnection();
    }

    @Test
    void addAndRemoveTest() throws EnrollmentDAOException, FacultyDAOException {
        Assert.assertEquals(fdao.getFacultyById(1, con).getTotalPlaces(), 10);
        Assert.assertEquals(fdao.getFacultyById(1, con).getBudgetPlaces(), 3);
        Assert.assertEquals(fdao.getFacultyById(2, con).getTotalPlaces(), 10);
        Assert.assertEquals(fdao.getFacultyById(2, con).getBudgetPlaces(), 2);
        Assert.assertEquals(fdao.getFacultyById(3, con).getTotalPlaces(), 10);
        Assert.assertEquals(fdao.getFacultyById(3, con).getBudgetPlaces(), 3);
        Assert.assertTrue(dao.add(1, 8, 0, con));
        Assert.assertThrows(EnrollmentDAOException.class,
                () -> dao.add(1, 8, 0, con));
        Assert.assertTrue(dao.add(1, 7, 1, con));
        Assert.assertTrue(dao.add(1, 6, 2, con));
        Assert.assertTrue(dao.add(2, 5, 0, con));
               dao.deleteApplicant(con, 2);
        dao.deleteFaculty(con, 1);
    }

    @Test
    void getEnrollmentTest() throws EnrollmentDAOException {
        List<Enrollment> enrollment = dao.getEnrollment(con);
        Assert.assertEquals(enrollment.size(), 9);
        Assert.assertTrue(dao.add(2, 4, 1, con));
        Assert.assertTrue(dao.add(2, 3, 2, con));
        enrollment = dao.getEnrollment(con);
        Assert.assertEquals(enrollment.size(), 11);
    }
}